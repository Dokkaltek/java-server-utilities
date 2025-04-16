package io.github.dokkaltek.util;

import io.github.dokkaltek.constant.literal.SpecialChars;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.owasp.encoder.Encode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.dokkaltek.util.StringUtils.isBlankOrNull;

/**
 * Utility class perform some actions on logs before sending them.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggingUtils {
    private static final String MASKED_FIELD = "*****";
    private static final String MASK_REGEX = "+(\\s*[=:]\\s*)(\\S*(?<!,))";

    /**
     * Encodes the passed object to avoid log forgery attacks.
     * @param object The object to encode as a string. Must have a valid .toString() method, or be a String.
     * @return The encoded object as a string.
     */
    public static String encodeForLog(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return Encode.forJava((String) object);
        }

        return Encode.forJava(object.toString());
    }

    /**
     * Masks the passed field of a message.
     * @param message The message to mask.
     * @param fieldsToMask The fields to mask.
     * @return The message with the masked field. <br>
     * For example, it could turn this: <br>
     * <code>password=123456</code> <br>
     * Into this: <br>
     * <code>password=*****</code>
     */
    public static String maskField(String message, String... fieldsToMask) {
        if (isBlankOrNull(message)) {
            return SpecialChars.EMPTY_STRING;
        }

        String maskRegex = generateMaskingRegex(fieldsToMask);

        if (isBlankOrNull(maskRegex)) {
            return message;
        }

        return message.replaceAll(maskRegex, "$1$2" + MASKED_FIELD);
    }

    /**
     * Masks the passed fields of a message in a specific range of characters.
     * @param message The message to mask.
     * @param start The start of the range.
     * @param end The end of the range.
     * @param fieldsToMask The fields to mask.
     * @return The message with the masked field.
     * For example: <br>
     * <code>email=some-email@email.com</code><br>
     * Would become for range 1, 4: <br>
     * <code>email=s****email@email.com</code>
     */
    public static String maskFieldRange(String message, int start, int end, String... fieldsToMask) {
        if (isBlankOrNull(message)) {
            return SpecialChars.EMPTY_STRING;
        }

        if (start >= end) {
            return message;
        }

        String maskRegex = generateMaskingRegex(fieldsToMask);

        if (isBlankOrNull(maskRegex)) {
            return message;
        }

        Matcher matcher = Pattern.compile(maskRegex).matcher(message);

        while (matcher.find()) {
            String field = matcher.group(1);
            String splitter = matcher.group(2);
            String value = matcher.group(3);
            int actualEnd = Math.min(value.length(), end);

            if (value.length() > start) {
                String maskedValue = value.substring(0, start) + StringUtils.repeat(SpecialChars.ASTERISK,
                        actualEnd - start) + value.substring(actualEnd);
                message = message.replace(field + splitter + value, field + splitter + maskedValue);
            }
        }
        return message;
    }

    /**
     * Masks the passed fields of a message up to the last occurrence of a character.
     * Can be useful to mask email addresses.
     * @param message The message to mask.
     * @param charToStopAt The character to stop the masking at.
     *                     It will mask up to the last occurrence of the character.
     * @param fieldsToMask The fields to mask.
     * @return The message with the masked field. <br>
     * For example: <br>
     * <code>email=some-email@email.com</code><br>
     * Would become: <br>
     * <code>email=**********@email.com</code>
     */
    public static String maskFieldUntilChar(String message, char charToStopAt, String... fieldsToMask) {
        if (isBlankOrNull(message)) {
            return SpecialChars.EMPTY_STRING;
        }

        String maskRegex = generateMaskingRegex(fieldsToMask);

        if (isBlankOrNull(maskRegex)) {
            return message;
        }

        Matcher matcher = Pattern.compile(maskRegex).matcher(message);

        while (matcher.find()) {
            String field = matcher.group(1);
            String splitter = matcher.group(2);
            String value = matcher.group(3);
            int end = value.lastIndexOf(charToStopAt);

            String maskedValue;
            if (end > 0) {
                maskedValue = StringUtils.repeat(SpecialChars.ASTERISK, end) + value.substring(end);
            } else {
                maskedValue = StringUtils.repeat(SpecialChars.ASTERISK, value.length());
            }
            message = message.replace(field + splitter + value, field + splitter + maskedValue);
        }
        return message;
    }

    /**
     * Masks the passed fields of a message up to the last occurrence of a character, and then in a specific range
     * after said character.
     * Can be useful to mask email addresses.
     * @param message The message to mask.
     * @param charToStopAt The character to stop the masking at.
     *                     It will mask up to the last occurrence of the character.
     * @param start The start of the range after the character to stop at.
     * @param end The end of the range after the character to stop at.
     * @param fieldsToMask The fields to mask.
     * @return The message with the masked field. <br>
     * For example: <br>
     * <code>email=some-email@email.com</code><br>
     * Would become with an int range of 0, 4: <br>
     * <code>email=**********@****l.com</code>
     */
    public static String maskFieldUntilCharWithRange(String message, char charToStopAt, int start, int end,
                                                     String... fieldsToMask) {
        if (isBlankOrNull(message)) {
            return SpecialChars.EMPTY_STRING;
        }

        String maskRegex = generateMaskingRegex(fieldsToMask);

        if (isBlankOrNull(maskRegex)) {
            return message;
        }

        Matcher matcher = Pattern.compile(maskRegex).matcher(message);

        while (matcher.find()) {
            String field = matcher.group(1);
            String splitter = matcher.group(2);
            String value = matcher.group(3);
            int charIndex = value.lastIndexOf(charToStopAt);

            String maskedValue;
            if (charIndex > 0) {
                String subValue = value.substring(charIndex + 1);
                if (start < end) {
                    int actualEnd = Math.min(subValue.length(), end);
                    if (subValue.length() > start) {
                        subValue = subValue.substring(0, start) + StringUtils.repeat(SpecialChars.ASTERISK,
                                actualEnd - start) + subValue.substring(actualEnd);
                    }
                }
                maskedValue = StringUtils.repeat(SpecialChars.ASTERISK, charIndex) + charToStopAt + subValue;
            } else {
                maskedValue = StringUtils.repeat(SpecialChars.ASTERISK, value.length());
            }
            message = message.replace(field + splitter + value, field + splitter + maskedValue);
        }
        return message;
    }

    /**
     * Generates the masking regex to use given the fields to mask.
     * @param fieldsToMask The fields to mask from the message.
     * @return The masking regex.
     */
    public static String generateMaskingRegex(String... fieldsToMask) {
        return generateMaskingRegex(SpecialChars.COMMA, fieldsToMask);
    }

    /**
     * Generates the masking regex to use given the fields to mask.
     * @param separator The separator to use to mark the end of a parameter. Specifies what other thing apart from
     *                  a space can mark the end of the field. If empty it defaults to a comma.
     * @param fieldsToMask The fields to mask from the message.
     * @return The masking regex.
     */
    public static String generateMaskingRegex(String separator, String... fieldsToMask) {
        StringBuilder fieldSelector = new StringBuilder("(");
        for (String field : fieldsToMask) {
            if (isBlankOrNull(field)) {
                continue;
            }
            fieldSelector.append(field).append("|");
        }

        if (fieldSelector.length() > 2) {
            fieldSelector.deleteCharAt(fieldSelector.length() - 1).append(")");
        } else {
            return SpecialChars.EMPTY_STRING;
        }

        String maskRegex = MASK_REGEX;
        if (!isBlankOrNull(separator) && !separator.equals(SpecialChars.COMMA)) {
            maskRegex = maskRegex.replace(SpecialChars.COMMA, separator);
        }

        return fieldSelector.append(maskRegex).toString();
    }
}
