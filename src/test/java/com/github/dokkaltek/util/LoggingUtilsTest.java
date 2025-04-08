package com.github.dokkaltek.util;

import com.github.dokkaltek.util.LoggingUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.dokkaltek.constant.literal.SpecialChars.EMPTY_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for {@link LoggingUtils} class.
 */
class LoggingUtilsTest {

    /**
     * Tests {@link LoggingUtils#encodeForLog(Object)} method.
     */
    @Test
    @DisplayName("Test encoding for log")
    void testEncodeForLog() {
        assertEquals("\\n\\nsomething\\n", LoggingUtils.encodeForLog("\n\nsomething\n"));
        assertEquals(EMPTY_STRING, LoggingUtils.encodeForLog(EMPTY_STRING));
        assertNull(LoggingUtils.encodeForLog(null));
    }

    /**
     * Tests {@link LoggingUtils#maskField(String, String...)} method.
     */
    @Test
    @DisplayName("Test masking a field")
    void testMaskField() {
        assertEquals("name: *****", LoggingUtils.maskField("name: guybrush", "name"));
        assertEquals("name=guybrush, password = *****",
                LoggingUtils.maskField("name=guybrush, password = test", "password"));
        assertEquals("name=*****, password =*****",
                LoggingUtils.maskField("name=guybrush, password =test", "name", "password"));
        assertEquals("name=guybrush", LoggingUtils.maskField("name=guybrush", (String) null));
        assertEquals(EMPTY_STRING, LoggingUtils.maskField(null, "name"));
    }

    /**
     * Tests {@link LoggingUtils#maskFieldRange(String, int, int, String...)} method.
     */
    @Test
    @DisplayName("Test masking a range of a field")
    void testMaskFieldRange() {
        assertEquals("name: ****rush", LoggingUtils.maskFieldRange("name: guybrush", 0, 4,"name"));
        assertEquals("name=guybrush, password = t**t",
                LoggingUtils.maskFieldRange("name=guybrush, password = test", 1, 3, "password"));
        assertEquals("name=*****ush, password =****",
                LoggingUtils.maskFieldRange("name=guybrush, password =test", 0, 5,"name", "password"));
        assertEquals("name: guybrush", LoggingUtils.maskFieldRange("name: guybrush", 0, 0,"name"));
        assertEquals("name: guybrush", LoggingUtils.maskFieldRange("name: guybrush", 5, 0,"name"));
        assertEquals("name: guybrush", LoggingUtils.maskFieldRange("name: guybrush", 5, 0, (String) null));
        assertEquals(EMPTY_STRING, LoggingUtils.maskFieldRange(null, 5, 0,"name"));
    }

    /**
     * Tests {@link LoggingUtils#maskFieldUntilChar(String, char, String...)} method.
     */
    @Test
    @DisplayName("Test masking a range of a field until a character")
    void testMaskFieldUntilChar() {
        assertEquals("email: ********@threepwood.com",
                LoggingUtils.maskFieldUntilChar("email: guybrush@threepwood.com", '@',"email"));
        assertEquals("tags=********#tag, password = test",
                LoggingUtils.maskFieldUntilChar("tags=some#tag#tag, password = test", '#', "tags"));
        assertEquals("name=guybrush, password =test",
                LoggingUtils.maskFieldUntilChar("name=guybrush, password =test", '@'));
        assertEquals("email: guybrush@threepwood.com",
                LoggingUtils.maskFieldUntilChar("email: guybrush@threepwood.com", '@',(String) null));
        assertEquals(EMPTY_STRING, LoggingUtils.maskFieldUntilChar(null, '@',"email"));
    }

    /**
     * Tests {@link LoggingUtils#maskFieldUntilCharWithRange(String, char, int, int, String...)} method.
     */
    @Test
    @DisplayName("Test masking a range of a field until a character")
    void testMaskFieldUntilCharWithRange() {
        assertEquals("email: ********@******wood.com",
                LoggingUtils.maskFieldUntilCharWithRange("email: guybrush@threepwood.com", '@', 0, 6, "email"));
        assertEquals("tags=********#t*g, password = test",
                LoggingUtils.maskFieldUntilCharWithRange("tags=some#tag#tag, password = test", '#', 1, 2,"tags"));
        assertEquals("name=guybrush, password =test",
                LoggingUtils.maskFieldUntilCharWithRange("name=guybrush, password =test", '@', 5, 6));
        assertEquals("email: guybrush@threepwood.com",
                LoggingUtils.maskFieldUntilCharWithRange("email: guybrush@threepwood.com", '@',0, 6, (String) null));
        assertEquals(EMPTY_STRING, LoggingUtils.maskFieldUntilCharWithRange(null, '@', 0, 6, "email"));
    }
}
