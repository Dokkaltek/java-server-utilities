package util;

import com.github.dokkaltek.exception.DateConversionException;
import com.github.dokkaltek.util.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static com.github.dokkaltek.util.DateUtils.getDefaultOffset;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link DateUtils} class.
 */
class DateUtilsTest {
    private static final String SAMPLE_DATE = "2020-05-01";
    private static final String SAMPLE_DATE_WITH_TIME = "2020-05-01 14:20:11";
    private static final String SAMPLE_DATE_WITH_TIME_MILLIS = "2020-05-01 14:20:11.321";
    private static final String SAMPLE_ISO_DATE_TIME = "2020-05-01T12:20:11.321Z";

    /**
     * Test for {@link DateUtils#getDefaultOffset()} method.
     */
    @Test
    void testGetDefaultOffset() {
        assertNotNull(getDefaultOffset());
    }

    /**
     * Test for {@link DateUtils#parseDate(String)} method.
     */
    @Test
    @DisplayName("Test date from string")
    void testParseDate() {
        Date date = DateUtils.parseDate(SAMPLE_DATE);

        assertNotNull(date);

        // Validate result
        assertEquals(2020, DateUtils.getYear(date));
        assertEquals(5, DateUtils.getMonth(date));
        assertEquals(1, DateUtils.getDay(date));
    }

    /**
     * Test for {@link DateUtils#parseDate(String)} method with time.
     */
    @Test
    @DisplayName("Test date from a string with time")
    void testParseDateWithTime() {
        Date date = DateUtils.parseDate(SAMPLE_DATE_WITH_TIME);

        assertNotNull(date);

        // Validate result
        assertEquals(2020, DateUtils.getYear(date));
        assertEquals(5, DateUtils.getMonth(date));
        assertEquals(1, DateUtils.getDay(date));
        assertEquals(14, DateUtils.getHours(date));
        assertEquals(20, DateUtils.getMinutes(date));
        assertEquals(11, DateUtils.getSeconds(date));
    }

    /**
     * Test for {@link DateUtils#parseDate(String)} method with time and millis.
     */
    @Test
    @DisplayName("Test date from a string with time and millis")
    void testParseDateWithTimeAndMillis() {
        Date date = DateUtils.parseDate(SAMPLE_DATE_WITH_TIME_MILLIS);

        assertNotNull(date);

        // Validate result
        assertEquals(2020, DateUtils.getYear(date));
        assertEquals(5, DateUtils.getMonth(date));
        assertEquals(1, DateUtils.getDay(date));
        assertEquals(14, DateUtils.getHours(date));
        assertEquals(20, DateUtils.getMinutes(date));
        assertEquals(11, DateUtils.getSeconds(date));
        assertEquals(321, DateUtils.getMillis(date));
    }

    /**
     * Test for {@link DateUtils#parseDate(String)} method with error.
     */
    @Test
    @DisplayName("Test date to string (Wrong date format)")
    void testParseDateWithError() {
        assertThrows(DateConversionException.class, () -> DateUtils.parseDate("wrong.date"));
    }

    /**
     * Test for {@link DateUtils#parseISODateTime(String)} method.
     */
    @Test
    @DisplayName("Test parsing an ISO date-time string")
    void testParseISODateTime() {
        Date date = DateUtils.parseISODateTime(SAMPLE_ISO_DATE_TIME);

        // Check without hour
        assertEquals(2020, DateUtils.getYear(date));
        assertEquals(5, DateUtils.getMonth(date));
        assertEquals(1, DateUtils.getDay(date));
        assertEquals(20, DateUtils.getMinutes(date));
        assertEquals(11, DateUtils.getSeconds(date));
        assertEquals(321, DateUtils.getMillis(date));
    }

    /**
     * Test for {@link DateUtils#parseOffsetDateTime(String)} method.
     */
    @Test
    @DisplayName("Test string to offset date time")
    void testParseOffsetDateTime() {
        OffsetDateTime date = DateUtils.parseOffsetDateTime(SAMPLE_DATE_WITH_TIME_MILLIS);

        assertEquals(2020, date.getYear());
        assertEquals(5, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(14, date.getHour());
        assertEquals(20, date.getMinute());
        assertEquals(11, date.getSecond());
        assertEquals(321_000_000, date.getNano());
        assertDoesNotThrow(() -> DateUtils.parseOffsetDateTime(SAMPLE_DATE));
        assertDoesNotThrow(() -> DateUtils.parseOffsetDateTime(SAMPLE_DATE_WITH_TIME.substring(0, 16)));
        assertDoesNotThrow(() -> DateUtils.parseOffsetDateTime(SAMPLE_DATE_WITH_TIME_MILLIS.replace(" ", "T")));
    }

    /**
     * Test for {@link DateUtils#parseOffsetDateTime(String)} method with error.
     */
    @Test
    @DisplayName("Test string to offset date time (Error)")
    void testParseOffsetDateTimeError() {
        assertThrows(DateConversionException.class, () -> DateUtils.parseOffsetDateTime("wrong.date"));
    }

    /**
     * Test for {@link DateUtils#parseLocalDateTime(String)} method.
     */
    @Test
    @DisplayName("Test string to local date time")
    void testParseLocalDateTime() {
        LocalDateTime date = DateUtils.parseLocalDateTime(SAMPLE_DATE_WITH_TIME_MILLIS);

        assertEquals(2020, date.getYear());
        assertEquals(5, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(14, date.getHour());
        assertEquals(20, date.getMinute());
        assertEquals(11, date.getSecond());
        assertEquals(321_000_000, date.getNano());
        assertDoesNotThrow(() -> DateUtils.parseLocalDateTime(SAMPLE_DATE));
        assertDoesNotThrow(() -> DateUtils.parseLocalDateTime(SAMPLE_DATE_WITH_TIME.substring(0, 16)));
        assertDoesNotThrow(() -> DateUtils.parseLocalDateTime(SAMPLE_DATE_WITH_TIME_MILLIS.replace(" ", "T")));
    }

    /**
     * Test for {@link DateUtils#parseLocalDateTime(String)} method with error.
     */
    @Test
    @DisplayName("Test string to local date time (Error)")
    void testParseLocalDateTimeError() {
        assertThrows(DateConversionException.class, () -> DateUtils.parseLocalDateTime("wrong.date"));
    }

    /**
     * Test for {@link DateUtils#formatToISODate(Date)} method.
     */
    @Test
    @DisplayName("Test date to ISO date string")
    void testFormatToISODate() {
        String isoDate = DateUtils.formatToISODate(getSampleDate());
        assertEquals(SAMPLE_DATE, isoDate);
    }

    /**
     * Test for {@link DateUtils#formatToISODate(OffsetDateTime)} method.
     */
    @Test
    @DisplayName("Test offset date time to ISO date string")
    void testFormatOffsetDateTimeToISODate() {
        String isoDate = DateUtils.formatToISODate(getSampleOffsetDateTime());
        assertEquals(SAMPLE_DATE, isoDate);
    }

    /**
     * Test for {@link DateUtils#formatToISODate(LocalDateTime)} method.
     */
    @Test
    @DisplayName("Test offset date time to ISO date string")
    void testFormatLocalDateTimeToISODate() {
        String isoDate = DateUtils.formatToISODate(getSampleLocalDateTime());
        assertEquals(SAMPLE_DATE, isoDate);
    }

    /**
     * Test for {@link DateUtils#formatToISODateTime(Date)} method.
     */
    @Test
    @DisplayName("Test date to ISO date time string")
    void testFormatToISODateTime() {
        String isoDate = DateUtils.formatToISODateTime(getSampleDate());

        // Check year and time, since those won't change
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(0, 4), isoDate.substring(0, 4));
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(14), isoDate.substring(14));
    }

    /**
     * Test for {@link DateUtils#formatToISODateTime(LocalDateTime)} method.
     */
    @Test
    @DisplayName("Test local date time to ISO date time string")
    void testFormatLocalDateTimeToISODateTime() {
        LocalDateTime localDateTime = getSampleLocalDateTime();
        String isoDate = DateUtils.formatToISODateTime(localDateTime);

        // Check year and time, since those won't change
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(0, 4), isoDate.substring(0, 4));
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(14), isoDate.substring(14));

        // Test not round nanos
        localDateTime = localDateTime.plusNanos(5);
        isoDate = DateUtils.formatToISODateTime(localDateTime);

        assertEquals(SAMPLE_ISO_DATE_TIME.substring(14), isoDate.substring(14));

        // Test short nanos result
        localDateTime = localDateTime.minusNanos(1_000_005);
        isoDate = DateUtils.formatToISODateTime(localDateTime);

        assertEquals("320Z", isoDate.substring(20));

        // Test short nanos result
        localDateTime = localDateTime.minusNanos(320_000_000);
        isoDate = DateUtils.formatToISODateTime(localDateTime);

        assertEquals("000Z", isoDate.substring(20));
    }

    /**
     * Test for {@link DateUtils#formatToDateTime(Date)} method.
     */
    @Test
    @DisplayName("Test date to date time string")
    void testFormatToDateTime() {
        String isoDate = DateUtils.formatToDateTime(getSampleDate());
        assertEquals(SAMPLE_DATE_WITH_TIME_MILLIS, isoDate);
    }

    /**
     * Test for {@link DateUtils#formatToDateTime(LocalDateTime)} method.
     */
    @Test
    @DisplayName("Test local date time to date time string")
    void testFormatLocalDateTimeToDateTime() {
        String isoDate = DateUtils.formatToDateTime(getSampleLocalDateTime());
        assertEquals(SAMPLE_DATE_WITH_TIME_MILLIS, isoDate);
    }

    /**
     * Test for {@link DateUtils#formatToDateTime(OffsetDateTime)} method.
     */
    @Test
    @DisplayName("Test offset date time to date time string")
    void testFormatOffsetDateTimeToDateTime() {
        String isoDate = DateUtils.formatToDateTime(getSampleOffsetDateTime());
        assertEquals(SAMPLE_DATE_WITH_TIME_MILLIS, isoDate);
    }

    /**
     * Test for {@link DateUtils#createDate(int, int, int)} method.
     */
    @Test
    @DisplayName("Test creating a date and modifying it with adders")
    void testCreateDate() {
        Date date = DateUtils.createDate(2020, 5, 1);
        assertEquals(2020, DateUtils.getYear(date));
        assertEquals(5, DateUtils.getMonth(date));
        assertEquals(1, DateUtils.getDay(date));

        date = DateUtils.addDays(date, 1);
        assertEquals(2, DateUtils.getDay(date));

        date = DateUtils.addMonths(date, 1);
        assertEquals(6, DateUtils.getMonth(date));

        date = DateUtils.addMonths(date, -1);
        assertEquals(5, DateUtils.getMonth(date));

        date = DateUtils.addYears(date, 1);
        assertEquals(2021, DateUtils.getYear(date));

        date = DateUtils.addHours(date, 1);
        assertEquals(1, DateUtils.getHours(date));

        date = DateUtils.addMinutes(date, 30);
        assertEquals(30, DateUtils.getMinutes(date));

        date = DateUtils.addSeconds(date, 30);
        assertEquals(30, DateUtils.getSeconds(date));

        date = DateUtils.addMillis(date, 500);
        assertEquals(500, DateUtils.getMillis(date));
    }

    // Sample date generation methods

    private Date getSampleDate() {
        return DateUtils.createDate(2020, 5, 1, 14, 20, 11, 321);
    }

    private LocalDateTime getSampleLocalDateTime() {
        return LocalDateTime.of(2020, 5, 1, 14, 20, 11, 321_000_000);
    }

    private OffsetDateTime getSampleOffsetDateTime() {
        return OffsetDateTime.of(getSampleLocalDateTime(), ZoneOffset.UTC);
    }
}
