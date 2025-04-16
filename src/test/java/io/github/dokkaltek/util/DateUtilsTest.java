package io.github.dokkaltek.util;

import io.github.dokkaltek.exception.DateConversionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Date;

import static io.github.dokkaltek.util.DateUtils.addMillis;
import static io.github.dokkaltek.util.DateUtils.getDefaultOffset;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link DateUtils} class.
 */
class DateUtilsTest {
    private static final String SAMPLE_DATE = "2020-05-01";
    private static final String SAMPLE_DATE_WITH_TIME = "2020-05-01 14:20:11";
    private static final String SAMPLE_DATE_WITH_TIME_MILLIS = "2020-05-01 14:20:11.321";
    private static final String SAMPLE_ISO_DATE_TIME = "2020-05-01T12:20:11.321Z";
    private static final String SAMPLE_TIME = "14:20:11";

    /**
     * Test for {@link DateUtils#getDefaultOffset()} method.
     */
    @Test
    @DisplayName("Test default offset")
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
        assertNull(DateUtils.parseDate(null));
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
     * Test for {@link DateUtils#parseISOInstant(String)} method.
     */
    @Test
    @DisplayName("Test parsing an ISO date-time string")
    void testParseISOInstant() {
        Date date = DateUtils.parseISOInstant(SAMPLE_ISO_DATE_TIME);

        // Check without hour
        assertEquals(2020, DateUtils.getYear(date));
        assertEquals(20, DateUtils.getMinutes(date));
        assertEquals(11, DateUtils.getSeconds(date));
        assertEquals(321, DateUtils.getMillis(date));
        assertNull(DateUtils.parseISOInstant(null));
    }

    /**
     * Test for {@link DateUtils#parseOffsetDateTime(String)} method.
     */
    @Test
    @DisplayName("Test string to offset date time")
    void testParseOffsetDateTime() {
        OffsetDateTime date = DateUtils.parseOffsetDateTime(SAMPLE_DATE_WITH_TIME_MILLIS);

        assertNotNull(date);
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
        assertDoesNotThrow(() -> DateUtils.parseOffsetDateTime(SAMPLE_ISO_DATE_TIME));
        assertDoesNotThrow(() -> DateUtils.parseOffsetDateTime(SAMPLE_ISO_DATE_TIME.replace("Z", "+0100")));
        assertNull(DateUtils.parseOffsetDateTime(null));
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

        assertNotNull(date);
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
        assertNull(DateUtils.parseLocalDateTime(null));
    }

    /**
     * Test for {@link DateUtils#parseLocalDateTimeFromISOInstant(String)} method.
     */
    @Test
    @DisplayName("Test ISO instant string to local date time")
    void testParseLocalDateTimeFromISOInstant() {
        assertDoesNotThrow(() -> DateUtils.parseLocalDateTimeFromISOInstant(SAMPLE_ISO_DATE_TIME));
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
     * Test for {@link DateUtils#parseLocalDate(String)} method.
     */
    @Test
    @DisplayName("Test ISO Date string to local date")
    void testParseLocalDate() {
        assertDoesNotThrow(() -> DateUtils.parseLocalDate(SAMPLE_DATE));
        assertNull(DateUtils.parseLocalDate(null));
    }

    /**
     * Test for {@link DateUtils#parseLocalTime(String)} method.
     */
    @Test
    @DisplayName("Test ISO local time string to local time")
    void testParseLocalTime() {
        assertDoesNotThrow(() -> DateUtils.parseLocalTime(SAMPLE_TIME));
        assertDoesNotThrow(() -> DateUtils.parseLocalTime(SAMPLE_TIME.substring(0, 5)));
        assertNull(DateUtils.parseLocalTime(null));
    }

    /**
     * Test for {@link DateUtils#parseOffsetTime(String)} method.
     */
    @Test
    @DisplayName("Test ISO offset time string to offset time")
    void testParseOffsetTime() {
        assertDoesNotThrow(() -> DateUtils.parseOffsetTime(SAMPLE_TIME + "+01:00"));
        assertDoesNotThrow(() -> DateUtils.parseOffsetTime(SAMPLE_TIME.substring(0, 5)));
        assertDoesNotThrow(() -> DateUtils.parseOffsetTime(SAMPLE_TIME + "Z"));
        assertNull(DateUtils.parseOffsetTime(null));
    }

    /**
     * Test for {@link DateUtils#formatToISODate(Date)} method.
     */
    @Test
    @DisplayName("Test date to ISO date string")
    void testFormatToISODate() {
        String isoDate = DateUtils.formatToISODate(getSampleDate());
        assertEquals(SAMPLE_DATE, isoDate);
        assertNull(DateUtils.formatToISODate((Date) null));
    }

    /**
     * Test for {@link DateUtils#formatToISODate(OffsetDateTime)} method.
     */
    @Test
    @DisplayName("Test offset date time to ISO date string")
    void testFormatOffsetDateTimeToISODate() {
        String isoDate = DateUtils.formatToISODate(getSampleOffsetDateTime());
        assertEquals(SAMPLE_DATE, isoDate);
        assertNull(DateUtils.formatToISODate((OffsetDateTime) null));
    }

    /**
     * Test for {@link DateUtils#formatToISODate(LocalDateTime)} method.
     */
    @Test
    @DisplayName("Test offset date time to ISO date string")
    void testFormatLocalDateTimeToISODate() {
        String isoDate = DateUtils.formatToISODate(getSampleLocalDateTime());
        assertEquals(SAMPLE_DATE, isoDate);
        assertNull(DateUtils.formatToISODate((LocalDateTime) null));
    }

    /**
     * Test for {@link DateUtils#formatToISODate(LocalDate)} method.
     */
    @Test
    @DisplayName("Test LocalDate to ISO date string")
    void testFormatToISOLocalDate() {
        String isoDate = DateUtils.formatToISODate(LocalDate.of(2020, 5, 1));
        assertEquals(SAMPLE_DATE, isoDate);
        assertNull(DateUtils.formatToISODate((LocalDate) null));
    }

    /**
     * Test for {@link DateUtils#formatToHourAndMinute(LocalTime)} method.
     */
    @Test
    @DisplayName("Test LocalTime to hour and minute string")
    void testFormatLocalTimeToHourAndMinute() {
        String time = DateUtils.formatToHourAndMinute(LocalTime.of(14, 20, 11));
        assertEquals(SAMPLE_TIME.substring(0, 5), time);
        assertNull(DateUtils.formatToHourAndMinute(null));
    }

    /**
     * Test for {@link DateUtils#formatToISOTime(LocalTime)} method.
     */
    @Test
    @DisplayName("Test LocalTime to hour and minute string")
    void testFormatLocalTimeToISOTime() {
        String time = DateUtils.formatToISOTime(LocalTime.of(14, 20, 11));
        assertEquals(SAMPLE_TIME, time);
        assertNull(DateUtils.formatToISOTime((LocalTime) null));
    }

    /**
     * Test for {@link DateUtils#formatToISOTime(OffsetTime)} method.
     */
    @Test
    @DisplayName("Test OffsetTime to hour and minute string")
    void testFormatOffsetTimeToISOTime() {
        String time = DateUtils.formatToISOTime(OffsetTime.of(LocalTime.of(14, 20, 11),
                ZoneOffset.of("+01:00")));
        assertEquals(SAMPLE_TIME + "+01:00", time);
        assertNull(DateUtils.formatToISOTime((OffsetTime) null));
    }

    /**
     * Test for {@link DateUtils#formatToISOInstant(Date)} method.
     */
    @Test
    @DisplayName("Test date to ISO date time string")
    void testFormatToISOInstant() {
        Date date = getSampleDate();
        String isoDate = DateUtils.formatToISOInstant(date);

        // Check year and time, since those won't change
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(0, 4), isoDate.substring(0, 4));
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(14), isoDate.substring(14));

        // Test not round nanos
        date = addMillis(date, -21);
        isoDate = DateUtils.formatToISOInstant(date);

        assertEquals("20:11.300Z", isoDate.substring(14));

        // Test short nanos result
        date = addMillis(date, -300);
        isoDate = DateUtils.formatToISOInstant(date);

        assertEquals("000Z", isoDate.substring(20));

        isoDate = DateUtils.formatToISOInstant(getSampleLocalDateTime(), ZoneOffset.of("+03:00"));

        assertEquals("2020-05-01T11:20:11.321Z", isoDate);
        assertNull(DateUtils.formatToISOInstant((Date) null));
    }

    /**
     * Test for {@link DateUtils#formatToISOInstant(Date)} method.
     */
    @Test
    @DisplayName("Test offset date time to ISO date time string")
    void testFormatOffsetDateTimeToISODateTime() {
        OffsetDateTime dateTime = getSampleOffsetDateTime();
        String isoDate = DateUtils.formatToISOInstant(dateTime);

        // Check year and time, since those won't change
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(0, 4), isoDate.substring(0, 4));
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(14), isoDate.substring(14));

        // Test not round nanos
        dateTime = dateTime.plusNanos(5);
        isoDate = DateUtils.formatToISOInstant(dateTime);

        assertEquals(SAMPLE_ISO_DATE_TIME.substring(14), isoDate.substring(14));

        // Test short nanos result
        dateTime = dateTime.minusNanos(1_000_005);
        isoDate = DateUtils.formatToISOInstant(dateTime);

        assertEquals("320Z", isoDate.substring(20));

        // Test short nanos result
        dateTime = dateTime.minusNanos(320_000_000);
        isoDate = DateUtils.formatToISOInstant(dateTime);

        assertEquals("000Z", isoDate.substring(20));
        assertNull(DateUtils.formatToISOInstant((OffsetDateTime) null));
    }

    /**
     * Test for {@link DateUtils#formatToISOInstant(LocalDateTime)} method.
     */
    @Test
    @DisplayName("Test local date time to ISO date time string")
    void testFormatLocalDateTimeToISODateTime() {
        LocalDateTime localDateTime = getSampleLocalDateTime();
        String isoDate = DateUtils.formatToISOInstant(localDateTime);

        // Check year and time, since those won't change
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(0, 4), isoDate.substring(0, 4));
        assertEquals(SAMPLE_ISO_DATE_TIME.substring(14), isoDate.substring(14));

        // Test not round nanos
        localDateTime = localDateTime.plusNanos(5);
        isoDate = DateUtils.formatToISOInstant(localDateTime);

        assertEquals(SAMPLE_ISO_DATE_TIME.substring(14), isoDate.substring(14));

        // Test short nanos result
        localDateTime = localDateTime.minusNanos(1_000_005);
        isoDate = DateUtils.formatToISOInstant(localDateTime);

        assertEquals("320Z", isoDate.substring(20));

        // Test short nanos result
        localDateTime = localDateTime.minusNanos(320_000_000);
        isoDate = DateUtils.formatToISOInstant(localDateTime);

        assertEquals("000Z", isoDate.substring(20));
        assertNull(DateUtils.formatToISOInstant((LocalDateTime) null));
    }

    /**
     * Test for {@link DateUtils#formatToDateTime(Date)} method.
     */
    @Test
    @DisplayName("Test date to date time string")
    void testFormatToDateTime() {
        String isoDate = DateUtils.formatToDateTime(getSampleDate());
        assertEquals(SAMPLE_DATE_WITH_TIME_MILLIS, isoDate);
        assertNull(DateUtils.formatToDateTime((Date) null));
    }

    /**
     * Test for {@link DateUtils#formatToDateTime(LocalDateTime)} method.
     */
    @Test
    @DisplayName("Test local date time to date time string")
    void testFormatLocalDateTimeToDateTime() {
        String isoDate = DateUtils.formatToDateTime(getSampleLocalDateTime());
        assertEquals(SAMPLE_DATE_WITH_TIME_MILLIS, isoDate);
        assertNull(DateUtils.formatToDateTime((LocalDateTime) null));
    }

    /**
     * Test for {@link DateUtils#formatToDateTime(OffsetDateTime)} method.
     */
    @Test
    @DisplayName("Test offset date time to date time string")
    void testFormatOffsetDateTimeToDateTime() {
        String isoDate = DateUtils.formatToDateTime(getSampleOffsetDateTime());
        assertEquals(SAMPLE_DATE_WITH_TIME_MILLIS, isoDate);
        assertNull(DateUtils.formatToDateTime((OffsetDateTime) null));
    }

    /**
     * Test for {@link DateUtils#createDate(int, int, int)} method.
     */
    @Test
    @DisplayName("Test creating a date and modifying it with adders")
    void testCreateDate() {
        Date date = DateUtils.createDate(2020, 5, 1);
        Date nullDate = null;
        assertEquals(2020, DateUtils.getYear(date));
        assertEquals(5, DateUtils.getMonth(date));
        assertEquals(1, DateUtils.getDay(date));

        date = DateUtils.addDays(date, 1);
        assertEquals(2, DateUtils.getDay(date));
        assertNull(DateUtils.addDays(nullDate, 1));

        date = DateUtils.addMonths(date, 1);
        assertEquals(6, DateUtils.getMonth(date));
        assertNull(DateUtils.addMonths(nullDate, 1));

        date = DateUtils.addMonths(date, -1);
        assertEquals(5, DateUtils.getMonth(date));

        date = DateUtils.addYears(date, 1);
        assertEquals(2021, DateUtils.getYear(date));
        assertNull(DateUtils.addYears(nullDate, 1));

        date = DateUtils.addHours(date, 1);
        assertEquals(1, DateUtils.getHours(date));
        assertNull(DateUtils.addHours(nullDate, 1));

        date = DateUtils.addMinutes(date, 30);
        assertEquals(30, DateUtils.getMinutes(date));
        assertNull(DateUtils.addMinutes(nullDate, 1));

        date = DateUtils.addSeconds(date, 30);
        assertEquals(30, DateUtils.getSeconds(date));
        assertNull(DateUtils.addSeconds(nullDate, 30));

        date = addMillis(date, 500);
        assertEquals(500, DateUtils.getMillis(date));
        assertNull(DateUtils.addMillis(nullDate, 500));
    }

    // Sample date generation methods

    private Date getSampleDate() {
        return DateUtils.createDate(2020, 5, 1, 14, 20, 11, 321);
    }

    private LocalDateTime getSampleLocalDateTime() {
        return LocalDateTime.of(2020, 5, 1, 14, 20, 11, 321_000_000);
    }

    private OffsetDateTime getSampleOffsetDateTime() {

        return OffsetDateTime.of(getSampleLocalDateTime(), ZoneOffset.of("+01:00"));
    }
}
