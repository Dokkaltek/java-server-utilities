package com.github.dokkaltek.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for string related operations. Most operations are null-safe.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {
    private static final Pattern WORD_PATTERN = Pattern.compile("\\S+");
    private static final Pattern SPACE_AND_CASE_DELIMITER = Pattern.compile("[\\s\\-_]+");
    private static final Pattern CAPS_PATTERN = Pattern.compile("(?<![\\-_A-Z])[A-Z]+");
    private static final String REPLACE_FIRST_MATCH = "$0";
    private static final String LOW_BAR = "_";
    private static final String BAR = "-";

    /**
     * Checks that a string is not blank or empty.
     * This method is only useful if you are using Java 8, otherwise you should use the one from Java 11 from
     * {@link String} class.
     * @param str The string to check.
     * @return True if the string is not blank, false otherwise.
     */
    public static boolean isBlank(String str) {
        if (str.isEmpty()) return true;
        return str.chars().allMatch(Character::isWhitespace);
    }

    /**
     * Checks if a string is blank, empty or null.
     * @param str The string to check.
     * @return True if the string is blank, empty or null, false otherwise.
     */
    public static boolean isBlankOrNull(String str) {
        return str == null || isBlank(str);
    }

    /**
     * Truncates a string to the given length if it is longer.
     * @param str The string to truncate.
     * @param length The length to truncate to.
     * @return The truncated string.
     */
    public static String truncate(String str, int length) {
        if (str == null || str.length() <= length || length < 0) {
            return str;
        }

        return str.substring(0, length);
    }

    /**
     * Concatenates a series of strings into one. Similar to <code>String.join()</code>, but without a delimiter.
     * @param strings The strings to concatenate. If null, an empty string will be returned.
     * @return The concatenated string.
     */
    public static String concatenate(String... strings) {
        if (strings == null || strings.length == 0 || (strings.length == 1 && strings[0] == null)) {
            return "";
        }

        // If only one string was passed, we return it as-is
        if (strings.length == 1) {
            return strings[0];
        }

        // Filter out null strings and concatenate
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            if (s != null) {
                builder.append(s);
            }
        }
        return builder.toString();
    }

    /**
     * Repeats a string the given number of times.
     * @param str The string to repeat.
     * @param times The number of times to repeat.
     * @return The string repeated the specified number of times.
     */
    public static String repeat(String str, int times) {
        if (str == null || times < 0) {
            return str;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * Pads a string to the given length adding spaces to it's left.
     * If the string is null, it will return null.
     * @param str The string to pad.
     * @param length The length reach after padding.
     * @return The padded string.
     */
    public static String padLeft(String str, int length) {
        return padLeftWithChar(str, length, ' ');
    }


    /**
     * Pads a string to the given length adding spaces to it's left.
     * If the string is null, it will return null.
     * @param str The string to pad.
     * @param length The length reach after padding.
     * @return The padded string.
     */
    public static String padRight(String str, int length) {
        return padRightWithChar(str, length, ' ');
    }

    /**
     * Pads a string to the given length adding spaces to it's left.
     * If the string is null, it will return null.
     * @param str The string to pad.
     * @param length The length reach after padding.
     * @param paddingChar The asChar to pad with.
     * @return The padded string.
     */
    public static String padLeftWithChar(String str, int length, char paddingChar) {
        if (str == null || str.length() >= length) {
            return str;
        }

        String padding = repeat(String.valueOf(paddingChar), length - str.length());
        return padding + str;
    }

    /**
     * Pads a string to the given length adding spaces to it's left.
     * If the string is null, it will return null.
     * @param str The string to pad.
     * @param length The length reach after padding.
     * @param paddingChar The asChar to pad with.
     * @return The padded string.
     */
    public static String padRightWithChar(String str, int length, char paddingChar) {
        if (str == null || str.length() >= length) {
            return str;
        }

        String padding = repeat(String.valueOf(paddingChar), length - str.length());
        return str + padding;
    }

    /**
     * Capitalizes a string.
     * <br>
     * For example: "hello world" -> "Hello world"
     * @param str The string to capitalize.
     * @return The capitalized string.
     */
    public static String capitalize(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        if (str.length() == 1) {
            return str.toUpperCase(Locale.getDefault());
        } else if (str.length() > 1 && !isBlank(str)) {
            String firstLetter = str.substring(0, 1).toUpperCase(Locale.getDefault());
            return firstLetter + str.substring(1);
        } else {
            return str;
        }
    }

    /**
     * Capitalizes all words of a string.
     * <br>
     * For example: "hello world" -> "Hello World"
     * @param str The string to capitalize.
     * @return The capitalized string.
     */
    public static String capitalizeAll(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        if (str.length() == 1) {
            return str.toUpperCase(Locale.getDefault());
        } else if (str.length() > 1 && !isBlank(str)) {
            Matcher match = WORD_PATTERN.matcher(str);
            StringBuilder capitalizedString = new StringBuilder();
            int prevIndex = 0;
            while (match.find()) {
                capitalizedString.append(str, prevIndex, match.start())
                        .append(String.valueOf(str.charAt(match.start()))
                        .toUpperCase(Locale.getDefault()));
                prevIndex = match.start() + 1;
            }
            capitalizedString.append(str, prevIndex, str.length());
            return capitalizedString.toString();
        } else {
            return str;
        }
    }

    /**
     * Converts a string to <b>snake_case</b> format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toSnakeCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        return switchCase(str, LOW_BAR).toLowerCase(Locale.getDefault());
    }

    /**
     * Converts a string to <b>SCREAMING_SNAKE_CASE</b> format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toScreamingSnakeCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        return switchCase(str, LOW_BAR).toUpperCase(Locale.getDefault());
    }

    /**
     * Converts a string to <b>kebab-case</b> format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toKebabCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        return switchCase(str, BAR).toLowerCase(Locale.getDefault());
    }

    /**
     * Converts a string to <b>SCREAMING-KEBAB-CASE</b> format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toScreamingKebabCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        return switchCase(str, BAR).toUpperCase(Locale.getDefault());
    }

    /**
     * Converts a string to <b>PascalCase</b> format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toPascalCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        String noCapsStr = escapeCaseCaps(str, LOW_BAR);

        String[] frags = SPACE_AND_CASE_DELIMITER.split(noCapsStr);
        StringBuilder sb = new StringBuilder();
        for (String frag : frags) {
            sb.append(frag.substring(0, 1).toUpperCase(Locale.getDefault()));
            sb.append(frag.substring(1).toLowerCase(Locale.getDefault()));
        }
        return sb.toString();
    }

    /**
     * Converts a string to <b>camelCase</b> format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toCamelCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        String noCapsStr = escapeCaseCaps(str, LOW_BAR);

        String[] frags = SPACE_AND_CASE_DELIMITER.split(noCapsStr);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < frags.length; i++) {
            String frag = frags[i];
            if (i > 0) {
                sb.append(frag.substring(0, 1).toUpperCase(Locale.getDefault()));
            } else {
                sb.append(String.valueOf(frag.charAt(0)).toLowerCase(Locale.getDefault()));
            }
            sb.append(frag.substring(1).toLowerCase(Locale.getDefault()));
        }
        return sb.toString();
    }

    /**
     * Escapes each non-escaped cap letter with the specified separator for switch case operations.
     * @param str The string to escape the caps of.
     * @param separator The separator to escape the caps.
     * @return The converted string.
     */
    private static String escapeCaseCaps(String str, String separator) {
        String escapedCapsStr = CAPS_PATTERN.matcher(str).replaceAll(separator + REPLACE_FIRST_MATCH);

        if (escapedCapsStr.startsWith(separator)) {
            escapedCapsStr = escapedCapsStr.substring(1);
        }

        return escapedCapsStr;
    }
    /**
     * Switches the case of a string with the specified separator.
     * @param str The string to switch the case to.
     * @param separator The separator to use.
     * @return The converted string.
     */
    private static String switchCase(String str, String separator) {
        String noCapsStr = escapeCaseCaps(str, separator);

        return SPACE_AND_CASE_DELIMITER.matcher(noCapsStr)
                .replaceAll(separator);
    }
}
