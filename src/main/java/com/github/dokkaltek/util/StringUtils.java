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
    private static final Pattern SPACE_AND_CASE_DELIMITER = Pattern.compile("[\\s-_]+");

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
        if (str == null || str.length() <= length) {
            return str;
        }

        return str.substring(0, length);
    }

    /**
     * Repeats a string the given number of times.
     * @param str The string to repeat.
     * @param times The number of times to repeat.
     * @return The repeated string.
     */
    public static String repeat(String str, int times) {
        if (str == null) {
            return null;
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
        if (str == null || str.length() >= length) {
            return str;
        }

        String padding = repeat(" ", length - str.length());
        return padding + str;
    }


    /**
     * Pads a string to the given length adding spaces to it's left.
     * If the string is null, it will return null.
     * @param str The string to pad.
     * @param length The length reach after padding.
     * @return The padded string.
     */
    public static String padRight(String str, int length) {
        if (str == null || str.length() >= length) {
            return str;
        }

        String padding = repeat(" ", length - str.length());
        return str + padding;
    }

    /**
     * Pads a string to the given length adding spaces to it's left.
     * If the string is null, it will return null.
     * @param str The string to pad.
     * @param length The length reach after padding.
     * @param paddingChar The character to pad with.
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
     * @param paddingChar The character to pad with.
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
        if (str == null) {
            return null;
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
        if (str == null) {
            return null;
        }

        if (str.length() == 1) {
            return str.toUpperCase(Locale.getDefault());
        } else if (str.length() > 1 && !isBlank(str)) {
            Matcher match = WORD_PATTERN.matcher(str);
            while (match.find()) {
                for (int i = 1; i <= match.groupCount(); i++) {
                    str = str.replaceFirst(match.group(i), capitalize(match.group(i)));
                }
            }
            return str;
        } else {
            return str;
        }
    }

    /**
     * Converts a string to snake_case format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toSnakeCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        return SPACE_AND_CASE_DELIMITER.matcher(str).replaceAll("_").toLowerCase(Locale.getDefault());
    }

    /**
     * Converts a string to SCREAMING_SNAKE_CASE format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toScreamingSnakeCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        return SPACE_AND_CASE_DELIMITER.matcher(str).replaceAll("_").toUpperCase(Locale.getDefault());
    }

    /**
     * Converts a string to kebab-case format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toKebabCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        return SPACE_AND_CASE_DELIMITER.matcher(str).replaceAll("-").toLowerCase(Locale.getDefault());
    }

    /**
     * Converts a string to kebab-case format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toScreamingKebabCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        return SPACE_AND_CASE_DELIMITER.matcher(str).replaceAll("-").toUpperCase(Locale.getDefault());
    }

    /**
     * Converts a string to PascalCase format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toPascalCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        String[] frags = SPACE_AND_CASE_DELIMITER.split(str);
        StringBuilder sb = new StringBuilder();
        for (String frag : frags) {
            sb.append(frag.substring(0, 1).toUpperCase(Locale.getDefault()));
            sb.append(frag.substring(1).toLowerCase(Locale.getDefault()));
        }
        return sb.toString();
    }

    /**
     * Converts a string to camelCase format.
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String toCamelCase(String str) {
        if (isBlankOrNull(str)) {
            return str;
        }

        String[] frags = SPACE_AND_CASE_DELIMITER.split(str);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < frags.length; i++) {
            String frag = frags[i];
            if (i > 0) {
                sb.append(frag.substring(0, 1).toUpperCase(Locale.getDefault()));
            } else {
                sb.append(frag.charAt(0));
            }
            sb.append(frag.substring(1).toLowerCase(Locale.getDefault()));
        }
        return sb.toString();
    }
}
