package com.github.dokkaltek.util;

import com.github.dokkaltek.constant.literal.SpecialChars;
import com.github.dokkaltek.exception.DateConversionException;
import com.github.dokkaltek.exception.InvalidEmailException;
import com.github.dokkaltek.exception.InvalidIdException;
import com.github.dokkaltek.exception.InvalidInputException;
import com.github.dokkaltek.exception.InvalidUUIDException;
import com.github.dokkaltek.exception.InvalidUriException;
import com.github.dokkaltek.exception.InvalidUrlException;
import com.github.dokkaltek.exception.JSONException;
import com.github.dokkaltek.util.ValidationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link ValidationUtils} class.
 */
class ValidationUtilsTest {
    private static final String SAMPLE_URL_NO_PATH = "https://test.com";
    private static final String WINDOWS_SYSTEM_PATH = "C:\\\\some\\path";
    private static final String SAMPLE_PATH = "some/path?there=was&some=param#andAFragmentToo";
    private static final String SAMPLE_URL_FTP = "ftp://test.com/some/path";
    private static final String INVALID_URI = "once upon a time";
    private static final String SAMPLE_JSON_POJO = "{\"description\":null,\"name\":\"John Doe\",\"age\":30}";
    private static final String INVALID_JSON = "{,.";

    /**
     * Tests {@link ValidationUtils#validateUUID(String)} method.
     */
    @Test
    @DisplayName("Test validating UUID")
    void testValidateUUID() {
        assertTrue(ValidationUtils.validateUUID("123e4567-e89b-12d3-a456-426655440000"));
        assertFalse(ValidationUtils.validateUUID("test"));
        assertFalse(ValidationUtils.validateUUID(null));
        assertFalse(ValidationUtils.validateUUID(SpecialChars.EMPTY_STRING));
    }

    /**
     * Tests {@link ValidationUtils#validateUUIDWithEx(String)} method.
     */
    @Test
    @DisplayName("Test validating UUID with exception")
    void testValidateUUIDWithEx() {
        assertDoesNotThrow(() -> ValidationUtils.validateUUIDWithEx("123e4567-e89b-12d3-a456-426655440000"));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateUUIDWithEx(null));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateUUIDWithEx(SpecialChars.EMPTY_STRING));
        assertThrows(InvalidUUIDException.class, () -> ValidationUtils.validateUUIDWithEx("test"));
    }

    /**
     * Test for {@link ValidationUtils#validateJSON(String)} method.
     */
    @Test
    @DisplayName("Test validating a json string")
    void testValidateJson() {
        assertTrue(ValidationUtils.validateJSON(SAMPLE_JSON_POJO));
        assertFalse(ValidationUtils.validateJSON(INVALID_JSON));
        assertFalse(ValidationUtils.validateJSON(null));
    }

    /**
     * Test for {@link ValidationUtils#validateJSONWithEx(String)} method.
     */
    @Test
    @DisplayName("Test validating a json string with exception")
    void testValidateJsonWithEx() {
        assertDoesNotThrow(() -> ValidationUtils.validateJSONWithEx(SAMPLE_JSON_POJO));
        assertThrows(JSONException.class, () -> ValidationUtils.validateJSONWithEx(INVALID_JSON));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateJSONWithEx(null));
    }

    /**
     * Test for {@link ValidationUtils#validateUri(String)} method.
     */
    @Test
    @DisplayName("Test validating any URI")
    void testValidateUri() {
        assertTrue(ValidationUtils.validateUri(SAMPLE_URL_NO_PATH));
        assertTrue(ValidationUtils.validateUri(SAMPLE_PATH));
        assertTrue(ValidationUtils.validateUri(SAMPLE_URL_FTP));
        assertFalse(ValidationUtils.validateUri(null));
        assertFalse(ValidationUtils.validateUri(INVALID_URI));
        assertFalse(ValidationUtils.validateUri(WINDOWS_SYSTEM_PATH));
    }

    /**
     * Test for {@link ValidationUtils#validateUrl(String)} method.
     */
    @Test
    @DisplayName("Test validating any URL")
    void testValidateUrl() {
        assertTrue(ValidationUtils.validateUrl(SAMPLE_URL_NO_PATH));
        assertTrue(ValidationUtils.validateUri(SAMPLE_URL_FTP));
        assertFalse(ValidationUtils.validateUrl(SAMPLE_PATH));
        assertFalse(ValidationUtils.validateUrl(null));
        assertFalse(ValidationUtils.validateUrl(INVALID_URI));
        assertFalse(ValidationUtils.validateUrl(WINDOWS_SYSTEM_PATH));
    }

    /**
     * Test for {@link ValidationUtils#validateUriWithEx(String)} method.
     */
    @Test
    @DisplayName("Test validating any URI with exception")
    void testValidateUriWithEx() {
        assertDoesNotThrow(() -> ValidationUtils.validateUriWithEx(SAMPLE_URL_NO_PATH));
        assertDoesNotThrow(() -> ValidationUtils.validateUriWithEx(SAMPLE_PATH));
        assertDoesNotThrow(() -> ValidationUtils.validateUriWithEx(SAMPLE_URL_FTP));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateUriWithEx(null));
        assertThrows(InvalidUriException.class, () -> ValidationUtils.validateUriWithEx(INVALID_URI));
        assertThrows(InvalidUriException.class, () -> ValidationUtils.validateUriWithEx(WINDOWS_SYSTEM_PATH));
    }

    /**
     * Test for {@link ValidationUtils#validateUrlWithEx(String)} method.
     */
    @Test
    @DisplayName("Test validating any URL with exception")
    void testValidateUrlWithEx() {
        assertDoesNotThrow(() -> ValidationUtils.validateUrlWithEx(SAMPLE_URL_NO_PATH));
        assertDoesNotThrow(() -> ValidationUtils.validateUrlWithEx(SAMPLE_URL_FTP));
        assertThrows(InvalidUrlException.class, () -> ValidationUtils.validateUrlWithEx(SAMPLE_PATH));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateUrlWithEx(null));
        assertThrows(InvalidUrlException.class, () -> ValidationUtils.validateUrlWithEx(INVALID_URI));
        assertThrows(InvalidUrlException.class, () -> ValidationUtils.validateUrlWithEx(WINDOWS_SYSTEM_PATH));
    }

    /**
     * Test for {@link ValidationUtils#validateDateFormat(String, String)} method.
     */
    @Test
    @DisplayName("Test validating a date with a given format")
    void testValidateDateFormat() {
        assertTrue(ValidationUtils.validateDateFormat("2022-01-01", "yyyy-MM-dd"));
        assertFalse(ValidationUtils.validateDateFormat("2022/01/01 01:01:01", "yyyy-MM-dd"));
        assertFalse(ValidationUtils.validateDateFormat(null, "yyyy-MM-dd"));
        assertFalse(ValidationUtils.validateDateFormat("2022-01-01", null));
    }

    /**
     * Test for {@link ValidationUtils#validateDateFormatWithEx(String, String)} method.
     */
    @Test
    @DisplayName("Test validating a date with a given format with exception")
    void testValidateDateFormatWithEx() {
        assertDoesNotThrow(() -> ValidationUtils.validateDateFormatWithEx("2022-01-01", "yyyy-MM-dd"));
        assertThrows(DateConversionException.class, () -> ValidationUtils.validateDateFormatWithEx("2022/01/01 01:01:01", "yyyy-MM-dd"));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateDateFormatWithEx(null, "yyyy-MM-dd"));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateDateFormatWithEx("2022-01-01", null));
    }

    /**
     * Test for {@link ValidationUtils#validateEmail(String)} method.
     */
    @Test
    @DisplayName("Test validating an email")
    void testValidateEmail() {
        assertTrue(ValidationUtils.validateEmail("test@email.com"));
        assertFalse(ValidationUtils.validateEmail("test@.email.com"));
        assertFalse(ValidationUtils.validateEmail("test@email.com."));
        assertFalse(ValidationUtils.validateEmail("test|@email.com."));
        assertFalse(ValidationUtils.validateEmail("test'@email.com."));
        assertFalse(ValidationUtils.validateEmail(null));
        assertFalse(ValidationUtils.validateEmail(SpecialChars.EMPTY_STRING));
    }

    /**
     * Test for {@link ValidationUtils#validateEmailWithEx(String)} method.
     */
    @Test
    @DisplayName("Test validating an email with exception")
    void testValidateEmailWithEx() {
        assertDoesNotThrow(() ->ValidationUtils.validateEmailWithEx("test@email.com"));
        assertThrows(InvalidEmailException.class, () -> ValidationUtils.validateEmailWithEx("test@.email.com"));
        assertThrows(InvalidEmailException.class, () -> ValidationUtils.validateEmailWithEx("test@email.com."));
        assertThrows(InvalidEmailException.class, () -> ValidationUtils.validateEmailWithEx("test|@email.com."));
        assertThrows(InvalidEmailException.class, () -> ValidationUtils.validateEmailWithEx("test'@email.com."));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateEmailWithEx(null));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateEmailWithEx(SpecialChars.EMPTY_STRING));
    }

    /**
     * Test for {@link ValidationUtils#validateNIF(String)} method.
     */
    @Test
    @DisplayName("Test validating a NIF")
    void testValidateNIF() {
        assertTrue(ValidationUtils.validateNIF("12345678z"));
        assertTrue(ValidationUtils.validateNIF("12345678Z"));
        assertFalse(ValidationUtils.validateNIF("123456789z"));
        assertFalse(ValidationUtils.validateNIF("12345678y"));
        assertFalse(ValidationUtils.validateNIF(null));
        assertFalse(ValidationUtils.validateNIF(SpecialChars.EMPTY_STRING));
    }

    /**
     * Test for {@link ValidationUtils#validateNIFWithEx(String)} method.
     */
    @Test
    @DisplayName("Test validating a NIF with exception")
    void testValidateNIFWithEx() {
        assertDoesNotThrow(() -> ValidationUtils.validateNIFWithEx("12345678z"));
        assertDoesNotThrow(() -> ValidationUtils.validateNIFWithEx("12345678Z"));
        assertThrows(InvalidIdException.class, () -> ValidationUtils.validateNIFWithEx("123456789z"));
        assertThrows(InvalidIdException.class, () -> ValidationUtils.validateNIFWithEx("12345678y"));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateNIFWithEx(null));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateNIFWithEx(SpecialChars.EMPTY_STRING));
    }

    /**
     * Test for {@link ValidationUtils#validateNIE(String)} method.
     */
    @Test
    @DisplayName("Test validating a NIE")
    void testValidateNIE() {
        assertTrue(ValidationUtils.validateNIE("Y8237411k"));
        assertTrue(ValidationUtils.validateNIE("Y8237411K"));
        assertFalse(ValidationUtils.validateNIE("X8237411k"));
        assertFalse(ValidationUtils.validateNIE("Z12345679p"));
        assertFalse(ValidationUtils.validateNIE(null));
        assertFalse(ValidationUtils.validateNIE(SpecialChars.EMPTY_STRING));
    }

    /**
     * Test for {@link ValidationUtils#validateNIEWithEx(String)} method.
     */
    @Test
    @DisplayName("Test validating a NIE with exception")
    void testValidateNIEWithEx() {
        assertDoesNotThrow(() -> ValidationUtils.validateNIEWithEx("Y8237411k"));
        assertDoesNotThrow(() -> ValidationUtils.validateNIEWithEx("Y8237411K"));
        assertThrows(InvalidIdException.class, () -> ValidationUtils.validateNIEWithEx("X8237411k"));
        assertThrows(InvalidIdException.class, () -> ValidationUtils.validateNIEWithEx("Z12345679p"));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateNIEWithEx(null));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateNIEWithEx(SpecialChars.EMPTY_STRING));
    }

    /**
     * Test for {@link ValidationUtils#validateDNI(String)} method.
     */
    @Test
    @DisplayName("Test validating a DNI")
    void testValidateDNI() {
        assertTrue(ValidationUtils.validateDNI("12345678Z"));
        assertTrue(ValidationUtils.validateDNI("Y8237411K"));
        assertFalse(ValidationUtils.validateDNI("X8237411k"));
        assertFalse(ValidationUtils.validateDNI("12345678y"));
        assertFalse(ValidationUtils.validateDNI(null));
        assertFalse(ValidationUtils.validateDNI(SpecialChars.EMPTY_STRING));
    }

    /**
     * Test for {@link ValidationUtils#validateDNIWithEx(String)} method.
     */
    @Test
    @DisplayName("Test validating a DNI with exception")
    void testValidateDNIWithEx() {
        assertDoesNotThrow(() -> ValidationUtils.validateDNIWithEx("12345678Z"));
        assertDoesNotThrow(() -> ValidationUtils.validateDNIWithEx("Y8237411K"));
        assertThrows(InvalidIdException.class, () -> ValidationUtils.validateDNIWithEx("X8237411k"));
        assertThrows(InvalidIdException.class, () -> ValidationUtils.validateDNIWithEx("12345678y"));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateDNIWithEx(null));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateDNIWithEx(SpecialChars.EMPTY_STRING));
    }
}
