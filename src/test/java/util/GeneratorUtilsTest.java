package util;

import com.github.dokkaltek.util.GeneratorUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import static com.github.dokkaltek.util.GeneratorUtils.generateRandomInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link GeneratorUtils} class.
 */
class GeneratorUtilsTest {

    /**
     * Test for {@link GeneratorUtils#generatePlaceholderText(int)} method.
     */
    @Test
    @DisplayName("Test generating placeholder text with different lengths")
    void testGeneratePlaceholderString() {
        // Test with bigger text than lorem ipsum placeholder of 445 length
        String placeholder = GeneratorUtils.generatePlaceholderText(446);
        assertNotNull(placeholder);
        assertEquals(446, placeholder.length());

        // Try with exact length
        placeholder = GeneratorUtils.generatePlaceholderText(445);
        assertEquals(445, placeholder.length());

        // Try with lower length
        placeholder = GeneratorUtils.generatePlaceholderText(5);
        assertEquals(5, placeholder.length());
    }

    /**
     * Test for {@link GeneratorUtils#generateSecureRandomString(int)} method.
     */
    @Test
    @DisplayName("Test generating a secure random string")
    void testGenerateSecureRandomString() {
        String secureRandomString = GeneratorUtils.generateSecureRandomString(20);
        assertNotNull(secureRandomString);
        assertEquals(20, secureRandomString.length());
    }

    /**
     * Test for {@link GeneratorUtils#generateSecureRandomString(int, String)} method.
     */
    @Test
    @DisplayName("Test generating a secure random string with allowed characters")
    void testGenerateSecureRandomStringWithAllowedCharacters() {
        String secureRandomString = GeneratorUtils.generateSecureRandomString(5, "a");
        assertNotNull(secureRandomString);
        assertEquals("aaaaa", secureRandomString);
    }

    /**
     * Test for {@link GeneratorUtils#generateSecureRandomString(int)} method.
     */
    @Test
    @DisplayName("Test generating a random string")
    void testGenerateRandomString() {
        String secureRandomString = GeneratorUtils.generateRandomString(20);
        assertNotNull(secureRandomString);
        assertEquals(20, secureRandomString.length());
    }

    /**
     * Test for {@link GeneratorUtils#generateSecureRandomString(int, String)} method.
     */
    @Test
    @DisplayName("Test generating a random string with allowed characters")
    void testGenerateRandomStringWithAllowedCharacters() {
        String secureRandomString = GeneratorUtils.generateRandomString(5, "a");
        assertNotNull(secureRandomString);
        assertEquals("aaaaa", secureRandomString);
    }

    /**
     * Test for {@link GeneratorUtils#generateRandomInt(int, int)} method.
     */
    @Test
    void testGenerateRandomInteger() {
        int number = generateRandomInt(-1, 1);
        assertTrue(number >= -1);
        assertTrue(number <= 1);
    }
}
