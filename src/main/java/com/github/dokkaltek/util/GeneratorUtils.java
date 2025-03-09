package com.github.dokkaltek.util;

import com.github.dokkaltek.constant.ErrorStatus;
import com.github.dokkaltek.exception.GenericException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class to generate sample data.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeneratorUtils {
    private static final Random RANDOM = new Random();
    private static final String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-_abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String LOREM_IPSUM_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
            "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor " +
            "in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
            "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    /**
     * Returns a "Lorem ipsum" type text with the given length.
     * @param length The length of the placeholder text to return.
     * @return The placeholder text with the given length.
     */
    public static String generatePlaceholderText(int length) {
        int loremLen = LOREM_IPSUM_TEXT.length();
        if (length <= 0) {
            return "";
        }

        if (length > loremLen) {
            int loremRepeats = (length / loremLen);
            if (length % loremLen > 0) {
                loremRepeats++;
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < loremRepeats; i++) {
                if (builder.length() > 0 && '.' == builder.charAt(builder.length() - 1)) {
                    builder.append(" ");
                }
                builder.append(LOREM_IPSUM_TEXT);
            }
            return builder.substring(0, length);
        } else if (length == loremLen) {
            return LOREM_IPSUM_TEXT;
        } else {
            return LOREM_IPSUM_TEXT.substring(0, length);
        }
    }

    /**
     * Generates a secure random string with the given length using <code>SecureRandom.getInstanceStrong()</code>.
     * The default allowed characters are <code>ABCDEFGHIJKLMNOPQRSTUVWXYZ-_abcdefghijklmnopqrstuvwxyz0123456789</code>.
     * @param length The length of the string to generate.
     * @return The generated string.
     */
    public static String generateSecureRandomString(int length) {
        return generateSecureRandomString(length, ALPHANUMERIC_CHARS);
    }

    /**
     * Generates a secure random string with the given length using <code>SecureRandom.getInstanceStrong()</code>.
     * @param length The length of the string to generate.
     * @param allowedCharacters A string with all the allowed characters for the generated string.
     * @return The generated string.
     */
    public static String generateSecureRandomString(int length, String allowedCharacters) {
        final SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            // Since all JDKs are supposed to provide at least one strong algorithm, this should never happen
            throw new GenericException("No strong algorithm available", e, ErrorStatus.INTERNAL_SERVER_ERROR.code());
        }

        return secureRandom
                .ints(length, 0, allowedCharacters.length())
                .mapToObj(allowedCharacters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    /**
     * Generates a random string with the given length.
     * The default allowed characters are <code>ABCDEFGHIJKLMNOPQRSTUVWXYZ-_abcdefghijklmnopqrstuvwxyz0123456789</code>.
     * @param length The length of the string to generate.
     * @return The generated string.
     */
    public static String generateRandomString(int length) {
        return generateRandomString(length, ALPHANUMERIC_CHARS);
    }

    /**
     * Generates a random string with the given length and allowed characters.
     * If you want to generate a secure random string, use {@link #generateSecureRandomString(int, String)} instead.
     * @param length The length of the string to generate.
     * @param allowedCharacters A string with all the allowed characters for the generated string.
     * @return The generated string.
     */
    public static String generateRandomString(int length, String allowedCharacters) {
        return RANDOM
                .ints(length, 0, allowedCharacters.length())
                .mapToObj(allowedCharacters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    /**
     * Generates a random integer between the min and max values (both inclusive).
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The generated integer.
     */
    public static int generateRandomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
