package util;

import com.github.dokkaltek.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.github.dokkaltek.util.StringUtils.capitalize;
import static com.github.dokkaltek.util.StringUtils.capitalizeAll;
import static com.github.dokkaltek.util.StringUtils.isBlankOrNull;
import static com.github.dokkaltek.util.StringUtils.padLeft;
import static com.github.dokkaltek.util.StringUtils.padLeftWithChar;
import static com.github.dokkaltek.util.StringUtils.padRight;
import static com.github.dokkaltek.util.StringUtils.padRightWithChar;
import static com.github.dokkaltek.util.StringUtils.repeat;
import static com.github.dokkaltek.util.StringUtils.toCamelCase;
import static com.github.dokkaltek.util.StringUtils.toKebabCase;
import static com.github.dokkaltek.util.StringUtils.toPascalCase;
import static com.github.dokkaltek.util.StringUtils.toScreamingKebabCase;
import static com.github.dokkaltek.util.StringUtils.toScreamingSnakeCase;
import static com.github.dokkaltek.util.StringUtils.toSnakeCase;
import static com.github.dokkaltek.util.StringUtils.truncate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link StringUtils} class.
 */
class StringUtilsTest {
    private static final String SAMPLE_LONG_STRING = "this is a long string";
    private static final String SAMPLE_SHORT_STRING = "bo";
    private static final String SAMPLE_CAMEL_CASE = "camelCase";
    private static final String SAMPLE_PASCAL_CASE = "PascalCase";
    private static final String SAMPLE_SNAKE_CASE = "snake_case";
    private static final String SAMPLE_KEBAB_CASE = "kebab-case";
    private static final String SAMPLE_BLANK_STRING = " ";

    /**
     * Test for {@link StringUtils#isBlankOrNull(String)} method.
     */
    @Test
    @DisplayName("Test checking if a string is blank or null")
    void testIsBlankOrNull() {
        assertTrue(isBlankOrNull(""));
        assertTrue(isBlankOrNull(null));
        assertFalse(isBlankOrNull("not empty"));
    }

    /**
     * Test for {@link StringUtils#truncate(String, int)} method.
     */
    @Test
    @DisplayName("Test truncating a string")
    void testTruncate() {
        assertEquals("this ", truncate(SAMPLE_LONG_STRING, 5));
        assertEquals(0, truncate("", 5).length());
        assertEquals("", truncate(SAMPLE_LONG_STRING, 0));
        assertEquals(SAMPLE_LONG_STRING, truncate(SAMPLE_LONG_STRING, -1));
    }

    /**
     * Test for {@link StringUtils#repeat(String, int)} method.
     */
    @Test
    @DisplayName("Test repeating a string")
    void testRepeat() {
        assertEquals("bobobo", repeat(SAMPLE_SHORT_STRING, 3));
        assertEquals("", repeat(SAMPLE_SHORT_STRING, 0));
        assertEquals(SAMPLE_SHORT_STRING, repeat(SAMPLE_SHORT_STRING, -1));
    }

    /**
     * Test for {@link StringUtils#padLeft(String, int)} method.
     */
    @Test
    @DisplayName("Test padding a string to the left")
    void testPadLeft() {
        assertEquals("   bo", padLeft(SAMPLE_SHORT_STRING, 5));
        assertEquals(SAMPLE_SHORT_STRING, padLeft(SAMPLE_SHORT_STRING, 0));
        assertEquals(SAMPLE_SHORT_STRING, padLeft(SAMPLE_SHORT_STRING, 2));
        assertEquals("  ", padLeft("", 2));
    }

    /**
     * Test for {@link StringUtils#padLeft(String, int)} method.
     */
    @Test
    @DisplayName("Test padding a string to the right")
    void testPadRight() {
        assertEquals("bo   ", padRight(SAMPLE_SHORT_STRING, 5));
        assertEquals(SAMPLE_SHORT_STRING, padRight(SAMPLE_SHORT_STRING, 0));
        assertEquals(SAMPLE_SHORT_STRING, padRight(SAMPLE_SHORT_STRING, 2));
        assertEquals("  ", padRight("", 2));
    }

    /**
     * Test for {@link StringUtils#padLeftWithChar(String, int, char)} method.
     */
    @Test
    @DisplayName("Test padding a string to the left with a char")
    void testPadLeftWithChar() {
        assertEquals("000bo", padLeftWithChar(SAMPLE_SHORT_STRING, 5, '0'));
        assertEquals(SAMPLE_SHORT_STRING, padLeftWithChar(SAMPLE_SHORT_STRING, 0, '0'));
        assertEquals(SAMPLE_SHORT_STRING, padLeftWithChar(SAMPLE_SHORT_STRING, 2, '0'));
        assertEquals("00", padLeftWithChar("", 2, '0'));
    }

    /**
     * Test for {@link StringUtils#padLeftWithChar(String, int, char)} method.
     */
    @Test
    @DisplayName("Test padding a string to the right with a char")
    void testPadRightWithChar() {
        assertEquals("bo000", padRightWithChar(SAMPLE_SHORT_STRING, 5, '0'));
        assertEquals(SAMPLE_SHORT_STRING, padRightWithChar(SAMPLE_SHORT_STRING, 0, '0'));
        assertEquals(SAMPLE_SHORT_STRING, padRightWithChar(SAMPLE_SHORT_STRING, 2, '0'));
        assertEquals("00", padRightWithChar("", 2, '0'));
    }

    /**
     * Test for {@link StringUtils#capitalize(String)} method.
     */
    @Test
    @DisplayName("Test capitalizing a string")
    void testCapitalize() {
        assertEquals("This is a long string", capitalize(SAMPLE_LONG_STRING));
        assertEquals("Bo", capitalize(SAMPLE_SHORT_STRING));
        assertEquals("A", capitalizeAll("a"));
        assertNull(capitalize(null));
        assertEquals(SAMPLE_BLANK_STRING, capitalize(SAMPLE_BLANK_STRING));
    }

    /**
     * Test for {@link StringUtils#capitalizeAll(String)} method.
     */
    @Test
    @DisplayName("Test capitalizing all words of a string")
    void testCapitalizeAll() {
        assertEquals("This Is A Long String", capitalizeAll(SAMPLE_LONG_STRING));
        assertEquals("Bo", capitalizeAll(SAMPLE_SHORT_STRING));
        assertEquals("A", capitalizeAll("a"));
        assertNull(capitalizeAll(null));
        assertEquals(SAMPLE_BLANK_STRING, capitalizeAll(SAMPLE_BLANK_STRING));
    }

    /**
     * Test for {@link StringUtils#toSnakeCase(String)} method.
     */
    @Test
    @DisplayName("Test switching to snake_case")
    void testToSnakeCase() {
        assertEquals("this_is_a_long_string", toSnakeCase(SAMPLE_LONG_STRING));
        assertEquals("camel_case", toSnakeCase(SAMPLE_CAMEL_CASE));
        assertEquals("pascal_case", toSnakeCase(SAMPLE_PASCAL_CASE));
        assertEquals("snake_case", toSnakeCase(SAMPLE_SNAKE_CASE));
        assertEquals("snake_case", toSnakeCase(SAMPLE_SNAKE_CASE.toUpperCase(Locale.getDefault())));
        assertEquals("kebab_case", toSnakeCase(SAMPLE_KEBAB_CASE));
        assertEquals("kebab_case", toSnakeCase(SAMPLE_KEBAB_CASE.toUpperCase(Locale.getDefault())));

        assertNull(toSnakeCase(null));
        assertEquals(SAMPLE_BLANK_STRING, toSnakeCase(SAMPLE_BLANK_STRING));
    }

    /**
     * Test for {@link StringUtils#toScreamingSnakeCase(String)} method.
     */
    @Test
    @DisplayName("Test switching to SCREAMING_SNAKE_CASE")
    void testToScreamingSnakeCase() {
        assertEquals("THIS_IS_A_LONG_STRING", toScreamingSnakeCase(SAMPLE_LONG_STRING));
        assertEquals("CAMEL_CASE", toScreamingSnakeCase(SAMPLE_CAMEL_CASE));
        assertEquals("PASCAL_CASE", toScreamingSnakeCase(SAMPLE_PASCAL_CASE));
        assertEquals("SNAKE_CASE", toScreamingSnakeCase(SAMPLE_SNAKE_CASE));
        assertEquals("SNAKE_CASE", toScreamingSnakeCase(SAMPLE_SNAKE_CASE.toUpperCase(Locale.getDefault())));
        assertEquals("KEBAB_CASE", toScreamingSnakeCase(SAMPLE_KEBAB_CASE));
        assertEquals("KEBAB_CASE", toScreamingSnakeCase(SAMPLE_KEBAB_CASE.toUpperCase(Locale.getDefault())));

        assertNull(toScreamingSnakeCase(null));
        assertEquals(SAMPLE_BLANK_STRING, toScreamingSnakeCase(SAMPLE_BLANK_STRING));
    }

    /**
     * Test for {@link StringUtils#toKebabCase(String)} method.
     */
    @Test
    @DisplayName("Test switching to kebab-case")
    void testToKebabCase() {
        assertEquals("this-is-a-long-string", toKebabCase(SAMPLE_LONG_STRING));
        assertEquals("camel-case", toKebabCase(SAMPLE_CAMEL_CASE));
        assertEquals("pascal-case", toKebabCase(SAMPLE_PASCAL_CASE));
        assertEquals("snake-case", toKebabCase(SAMPLE_SNAKE_CASE));
        assertEquals("snake-case", toKebabCase(SAMPLE_SNAKE_CASE.toUpperCase(Locale.getDefault())));
        assertEquals("kebab-case", toKebabCase(SAMPLE_KEBAB_CASE));
        assertEquals("kebab-case", toKebabCase(SAMPLE_KEBAB_CASE.toUpperCase(Locale.getDefault())));

        assertNull(toKebabCase(null));
        assertEquals(SAMPLE_BLANK_STRING, toKebabCase(SAMPLE_BLANK_STRING));
    }

    /**
     * Test for {@link StringUtils#toScreamingKebabCase(String)} method.
     */
    @Test
    @DisplayName("Test switching to SCREAMING_KEBAB_CASE")
    void testToScreamingKebabCase() {
        assertEquals("THIS-IS-A-LONG-STRING", toScreamingKebabCase(SAMPLE_LONG_STRING));
        assertEquals("CAMEL-CASE", toScreamingKebabCase(SAMPLE_CAMEL_CASE));
        assertEquals("PASCAL-CASE", toScreamingKebabCase(SAMPLE_PASCAL_CASE));
        assertEquals("SNAKE-CASE", toScreamingKebabCase(SAMPLE_SNAKE_CASE));
        assertEquals("SNAKE-CASE", toScreamingKebabCase(SAMPLE_SNAKE_CASE.toUpperCase(Locale.getDefault())));
        assertEquals("KEBAB-CASE", toScreamingKebabCase(SAMPLE_KEBAB_CASE));
        assertEquals("KEBAB-CASE", toScreamingKebabCase(SAMPLE_KEBAB_CASE.toUpperCase(Locale.getDefault())));

        assertNull(toScreamingKebabCase(null));
        assertEquals(SAMPLE_BLANK_STRING, toScreamingKebabCase(SAMPLE_BLANK_STRING));
    }

    /**
     * Test for {@link StringUtils#toPascalCase(String)} method.
     */
    @Test
    @DisplayName("Test switching to PascalCase")
    void testToPascalCase() {
        assertEquals("ThisIsALongString", toPascalCase(SAMPLE_LONG_STRING));
        assertEquals("CamelCase", toPascalCase(SAMPLE_CAMEL_CASE));
        assertEquals("PascalCase", toPascalCase(SAMPLE_PASCAL_CASE));
        assertEquals("SnakeCase", toPascalCase(SAMPLE_SNAKE_CASE));
        assertEquals("SnakeCase", toPascalCase(SAMPLE_SNAKE_CASE.toUpperCase(Locale.getDefault())));
        assertEquals("KebabCase", toPascalCase(SAMPLE_KEBAB_CASE));
        assertEquals("KebabCase", toPascalCase(SAMPLE_KEBAB_CASE.toUpperCase(Locale.getDefault())));

        assertNull(toPascalCase(null));
        assertEquals(SAMPLE_BLANK_STRING, toPascalCase(SAMPLE_BLANK_STRING));
    }

    /**
     * Test for {@link StringUtils#toCamelCase(String)} method.
     */
    @Test
    @DisplayName("Test switching to camelCase")
    void testToCamelCase() {
        assertEquals("thisIsALongString", toCamelCase(SAMPLE_LONG_STRING));
        assertEquals("camelCase", toCamelCase(SAMPLE_CAMEL_CASE));
        assertEquals("pascalCase", toCamelCase(SAMPLE_PASCAL_CASE));
        assertEquals("snakeCase", toCamelCase(SAMPLE_SNAKE_CASE));
        assertEquals("snakeCase", toCamelCase(SAMPLE_SNAKE_CASE.toUpperCase(Locale.getDefault())));
        assertEquals("kebabCase", toCamelCase(SAMPLE_KEBAB_CASE));
        assertEquals("kebabCase", toCamelCase(SAMPLE_KEBAB_CASE.toUpperCase(Locale.getDefault())));

        assertNull(toCamelCase(null));
        assertEquals(SAMPLE_BLANK_STRING, toCamelCase(SAMPLE_BLANK_STRING));
    }

}
