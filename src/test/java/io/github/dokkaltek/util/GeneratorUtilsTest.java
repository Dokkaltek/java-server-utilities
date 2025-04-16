package io.github.dokkaltek.util;

import io.github.dokkaltek.exception.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static io.github.dokkaltek.constant.literal.SpecialChars.EMPTY_STRING;
import static io.github.dokkaltek.util.GeneratorUtils.generateRandomInt;
import static io.github.dokkaltek.util.GeneratorUtils.getSecureRandomInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

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

        // Try with zero length
        placeholder = GeneratorUtils.generatePlaceholderText(0);
        assertEquals(EMPTY_STRING, placeholder);
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
        assertNull(GeneratorUtils.generateSecureRandomString(-1));
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
        assertNull(GeneratorUtils.generateSecureRandomString(5, null));
        assertNull(GeneratorUtils.generateSecureRandomString(5, EMPTY_STRING));
    }

    /**
     * Test for {@link GeneratorUtils#generateRandomString(int)} method.
     */
    @Test
    @DisplayName("Test generating a random string")
    void testGenerateRandomString() {
        String secureRandomString = GeneratorUtils.generateRandomString(20);
        assertNotNull(secureRandomString);
        assertEquals(20, secureRandomString.length());

        assertNull(GeneratorUtils.generateRandomString(-1));
    }

    /**
     * Test for {@link GeneratorUtils#generateRandomString(int, String)} method.
     */
    @Test
    @DisplayName("Test generating a random string with allowed characters")
    void testGenerateRandomStringWithAllowedCharacters() {
        String secureRandomString = GeneratorUtils.generateRandomString(5, "a");
        assertNotNull(secureRandomString);
        assertEquals("aaaaa", secureRandomString);
        assertNull(GeneratorUtils.generateRandomString(5, null));
        assertNull(GeneratorUtils.generateRandomString(5, EMPTY_STRING));
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

    /**
     * Test for {@link GeneratorUtils#getSecureRandomInstance()} method.
     */
    @Test
    void testGetSecureRandomInstance() {
        assertNotNull(getSecureRandomInstance());

        // Test error case too
        try(MockedStatic<SecureRandom> secureRandom = mockStatic(SecureRandom.class)) {
            secureRandom.when(SecureRandom::getInstanceStrong).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(CryptoException.class, GeneratorUtils::getSecureRandomInstance);
        }
    }
}
