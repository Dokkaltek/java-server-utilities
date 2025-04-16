package io.github.dokkaltek.util;

import io.github.dokkaltek.constant.literal.Letters;
import io.github.dokkaltek.constant.literal.SpecialChars;
import io.github.dokkaltek.exception.DateConversionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import static io.github.dokkaltek.constant.DateFormats.DATE_TIME_FORMAT;
import static io.github.dokkaltek.constant.DateFormats.DATE_TIME_MILLI_FORMAT;
import static io.github.dokkaltek.constant.DateFormats.HOUR_AND_MINUTE_FORMAT;
import static io.github.dokkaltek.constant.DateFormats.ISO_DATE_FORMAT;
import static io.github.dokkaltek.util.StringUtils.isBlankOrNull;

/**
 * Utility class to perform date related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {
    private static final String DATE_TIME_FORMATTER_PATTERN = "yyyy-MM-dd[[ ]['T']HH:mm[:ss[.SSS]]]";
    private static final int ISO_INSTANT_LENGTH_WITHOUT_MILLIS = 23;

    /**
     * Gets the {@link ZoneOffset} for the current time zone of the machine.
     * @return The {@link ZoneOffset} for the current time zone of the machine.
     */
    public static ZoneOffset getDefaultOffset() {
        return OffsetTime.now().getOffset();
    }

    /**
     * Gets a date from a string in <code>yyyy-MM-dd</code> format with optional <code>HH:mm[:ss[.SSS]]</code> format
     * time.
     * <br><br>
     * Example input values:
     * <ul>
     *     <li>2020-05-01</li>
     *     <li>2020-05-01 05:20</li>
     *     <li>2020-05-01 05:20:11</li>
     *     <li>2020-05-01 05:20:11.321</li>
     * </ul>
     *
     * @param date The date string to convert to a date.
     * @return The {@link Date} parsed from the string.
     */
    public static Date parseDate(String date) {
        if (isBlankOrNull(date))
            return null;
        String formatToUse = ISO_DATE_FORMAT;
        if (date.contains(":")) {
            if (date.contains(".")) {
                formatToUse = DATE_TIME_MILLI_FORMAT;
            } else {
                formatToUse = DATE_TIME_FORMAT;
            }
        }

        return parseDate(date, formatToUse);
    }

    /**
     * Gets a date from a string in the specified format. If date or format is null or empty, returns null.
     * @param date The date string to convert to a {@link Date}.
     * @param format The format of the date string.
     * @return The {@link Date} parsed from the string.
     */
    public static Date parseDate(String date, String format) {
        if (isBlankOrNull(date) || isBlankOrNull(format)) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        try {
            return formatter.parse(date.trim());
        } catch (ParseException e) {
            throw new DateConversionException(e);
        }
    }

    /**
     * Parses an ISO UTC date-time string in the format <code>yyyy-MM-ddTHH:mm:ss.SSSZ</code> to a {@link Date}.
     *
     * @param date The ISO date-time string to parse.
     * @return The parsed {@link Date}.
     */
    public static Date parseISOInstant(String date) {
        if (isBlankOrNull(date))
            return null;

        return Date.from(Instant.parse(date));
    }

    /**
     * Gets an {@link OffsetDateTime} from a string in <code>yyyy-MM-dd[[ ]['T']HH:mm[:ss[.SSS]]]</code> format using
     * using the machine default time offset.
     * <br><br>
     * Example input values:
     * <ul>
     *     <li>2020-05-01</li>
     *     <li>2020-05-01 05:20</li>
     *     <li>2020-05-01 05:20:11</li>
     *     <li>2020-05-01 05:20:11.321</li>
     *     <li>2020-05-01T05:20:11.321</li>
     *     <li>2020-05-01T04:20:11.321Z</li>
     *     <li>2020-05-01T04:20:11.321+0100</li>
     * </ul>
     * @param date The date string to convert to an {@link OffsetDateTime}.
     * @return The {@link OffsetDateTime} parsed from the string.
     */
    public static OffsetDateTime parseOffsetDateTime(String date) {
        if (isBlankOrNull(date))
            return null;

        // Add default time if missing
        if (!date.contains(":")) {
            date += " 00:00:00";
        }

        try {
            // Add the offset if it didn't have one already
            String offset = "";
            if (date.contains("Z")) {
                date = date.replace("Z", "");
                offset = " " + ZoneOffset.UTC.toString().replace(SpecialChars.COLON, SpecialChars.EMPTY_STRING);
            }


            if (date.length() > 10 && !date.substring(10).contains("+") &&
                    !date.substring(10).contains("-")) {
                offset = " " + getDefaultOffset().toString().replace(SpecialChars.COLON, SpecialChars.EMPTY_STRING);
            }

            return OffsetDateTime.parse(date.trim() + offset,
                    DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN + "[ ]Z"));
        } catch (DateTimeException e) {
            throw new DateConversionException(e);
        }
    }

    /**
     * Gets a {@link LocalDateTime} from a string in <code>yyyy-MM-dd[[ ]['T']HH:mm[:ss[.SSS]]]</code> format.
     * <br><br>
     * Example input values:
     * <ul>
     *     <li>2020-05-01</li>
     *     <li>2020-05-01 05:20</li>
     *     <li>2020-05-01 05:20:11</li>
     *     <li>2020-05-01 05:20:11.321</li>
     *     <li>2020-05-01T05:20:11.321</li>
     * </ul>
     * @param date The date string to convert to a {@link LocalDateTime}.
     * @return The {@link LocalDateTime} parsed from the string.
     */
    public static LocalDateTime parseLocalDateTime(String date) {
        if (isBlankOrNull(date))
            return null;

        // Add default time if missing
        if (!date.contains(":")) {
            date += " 00:00:00";
        }

        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN));
        } catch (DateTimeException e) {
            throw new DateConversionException(e);
        }
    }

    /**
     * Parses an ISO UTC date-time string in the format <code>yyyy-MM-ddTHH:mm:ss.SSSZ</code> to a
     * {@link LocalDateTime}.
     *
     * @param date The ISO date-time string to parse.
     * @return The parsed {@link Date}.
     */
    public static LocalDateTime parseLocalDateTimeFromISOInstant(String date) {
        if (isBlankOrNull(date))
            return null;
        return LocalDateTime.ofInstant(Instant.parse(date), getDefaultOffset());
    }

    /**
     * Gets a {@link LocalDate} from a string in <code>yyyy-MM-dd</code> format.
     * @param date The date string to convert to a {@link LocalDate}.
     * @return The {@link LocalDate} parsed from the string.
     */
    public static LocalDate parseLocalDate(String date) {
        if (isBlankOrNull(date))
            return null;

        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeException e) {
            throw new DateConversionException(e);
        }
    }

    /**
     * Gets a {@link LocalTime} from a string in <code>HH:mm[:ss]</code> format.
     * @param date The date string to convert to a {@link LocalTime}.
     * @return The {@link LocalTime} parsed from the string.
     */
    public static LocalTime parseLocalTime(String date) {
        if (isBlankOrNull(date))
            return null;

        try {
            return LocalTime.parse(date, DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (DateTimeException e) {
            throw new DateConversionException(e);
        }
    }

    /**
     * Gets a {@link OffsetTime} from a string in <code>HH:mm[[:ss]Z]</code> format.
     * <br><br>
     * Example input values:
     *  <ul>
     *  <li>05:20</li>
     *  <li>05:20:11</li>
     *  <li>04:20:11Z</li>
     *  <li>04:20:11+01:00</li>
     * </ul>
     * @param date The date string to convert to a {@link OffsetTime}.
     * @return The {@link OffsetTime} parsed from the string.
     */
    public static OffsetTime parseOffsetTime(String date) {
        if (isBlankOrNull(date))
            return null;

        if (!date.contains(SpecialChars.PLUS) && !date.contains(SpecialChars.MINUS)) {
            ZoneOffset offset = getDefaultOffset();
            if (date.contains(Letters.Z.name())) {
                date = date.replace(Letters.Z.name(), SpecialChars.EMPTY_STRING);
                offset = ZoneOffset.UTC;
            }
            return OffsetTime.of(Objects.requireNonNull(parseLocalTime(date)), offset);
        }

        try {
            return OffsetTime.parse(date, DateTimeFormatter.ISO_OFFSET_TIME);
        } catch (DateTimeException e) {
            throw new DateConversionException(e);
        }
    }

    /**
     * Converts a {@link Date} to a simple string in <code>yyyy-MM-dd</code> format.
     * @param date The {@link Date} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISODate(Date date) {
        return formatDate(date, ISO_DATE_FORMAT);
    }

    /**
     * Converts an {@link OffsetDateTime} to a simple string in <code>yyyy-MM-dd</code> format.
     * @param offsetDateTime The {@link OffsetDateTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISODate(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null)
            return null;
        return offsetDateTime.format(DateTimeFormatter.ofPattern(ISO_DATE_FORMAT));
    }

    /**
     * Converts a {@link LocalDateTime} to an ISO date string in <code>yyyy-MM-dd</code> format.
     * @param localDateTime The {@link LocalDateTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISODate(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;
        return localDateTime.format(DateTimeFormatter.ISO_DATE);
    }

    /**
     * Converts a {@link LocalDate} to a simple string in <code>yyyy-MM-dd</code> format.
     * @param localDate The {@link LocalDate} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISODate(LocalDate localDate) {
        if (localDate == null)
            return null;
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }

    /**
     * Converts a {@link LocalTime} to a simple string in <code>HH:mm</code> format.
     * @param localTime The {@link LocalTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToHourAndMinute(LocalTime localTime) {
        if (localTime == null)
            return null;
        return localTime.format(DateTimeFormatter.ofPattern(HOUR_AND_MINUTE_FORMAT));
    }

    /**
     * Converts a {@link LocalTime} to a simple string in <code>HH:mm:ss</code> format.
     * @param localTime The {@link LocalTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISOTime(LocalTime localTime) {
        if (localTime == null)
            return null;
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    /**
     * Converts an {@link OffsetTime} to a simple string in <code>HH:mm[:ss[Z]]</code> format.
     * @param offsetTime The {@link OffsetTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISOTime(OffsetTime offsetTime) {
        if (offsetTime == null)
            return null;
        return offsetTime.format(DateTimeFormatter.ISO_TIME);
    }

    /**
     * Converts a {@link Date} to a UTC ISO date-time string in <code>yyyy-MM-ddTHH:mm:ss.SSSZ</code> format.
     * @param date The {@link Date} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISOInstant(Date date) {
        if (date == null)
            return null;
        String dateString = String.valueOf(date.toInstant());

        // Make sure we have milliseconds, even if they are 0
        return (enforceMillisOnInstantString(dateString));
    }

    /**
     * Converts a {@link OffsetDateTime} to a UTC zone ISO date-time string in
     * <code>yyyy-MM-ddTHH:mm:ss.SSSZ</code> format.
     * @param offsetDateTime The {@link OffsetDateTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISOInstant(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null)
            return null;
        String dateString = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT);

        // Make sure we have milliseconds, even if they are 0
        return (enforceMillisOnInstantString(dateString));
    }

    /**
     * Converts a {@link LocalDateTime} to a UTC zone ISO date-time string in
     * <code>yyyy-MM-ddTHH:mm:ss.SSSZ</code> format.
     * @param localDateTime The {@link LocalDateTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToISOInstant(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;
        return formatToISOInstant(localDateTime, getDefaultOffset());
    }

    /**
     * Converts a {@link LocalDateTime} to a UTC zone ISO date-time string in
     * <code>yyyy-MM-ddTHH:mm:ss.SSSZ</code> format using the specified offset to calculate from UTC.
     * @param localDateTime The {@link LocalDateTime} to convert to string.
     * @param offset The {@link ZoneOffset} to use.
     * @return The string representation of the date.
     */
    public static String formatToISOInstant(LocalDateTime localDateTime, ZoneOffset offset) {
        if (localDateTime == null)
            return null;
        if (offset == null)
            offset = getDefaultOffset();

        String dateString = String.valueOf(localDateTime.toInstant(offset));

        // Make sure we have milliseconds, even if they are 0
        return (enforceMillisOnInstantString(dateString));
    }

    /**
     * Converts a {@link Date} to a string in <code>yyyy-MM-dd HH:mm:ss.SSS</code> format.
     * @param date The {@link Date} to convert to string.
     * @return The string representation of the date with time included.
     */
    public static String formatToDateTime(Date date) {
        return formatDate(date, DATE_TIME_MILLI_FORMAT);
    }

    /**
     * Converts an {@link OffsetDateTime} to an ISO date string in <code>yyyy-MM-dd HH:mm:ss.SSS</code> format.
     * @param offsetDateTime The {@link OffsetDateTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToDateTime(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null)
            return null;
        return offsetDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_MILLI_FORMAT));
    }

    /**
     * Converts a {@link LocalDateTime} to an ISO date string in <code>yyyy-MM-dd HH:mm:ss.SSS</code> format.
     * @param localDateTime The {@link LocalDateTime} to convert to string.
     * @return The string representation of the date.
     */
    public static String formatToDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_MILLI_FORMAT));
    }

    /**
     * Converts a {@link Date} to a string in the specified format.
     * @param date The {@link Date} to convert to string.
     * @param format The format of the date string.
     * @return The string representation of the date.
     */
    public static String formatDate(Date date, String format) {
        if (date == null || isBlankOrNull(format))
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * Converts a {@link Date} to a {@link GregorianCalendar}.
     * @param date The date to convert.
     * @return The converted {@link GregorianCalendar}.
     */
    public static GregorianCalendar convertToGregorianCalendar(Date date) {
        if (date == null)
            return null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Adds years to a {@link Date}.
     * You can subtract by passing a negative number.
     * @param date The date to add years to.
     * @param years The number of years to add.
     * @return The new {@link Date}.
     */
    public static Date addYears(Date date, int years) {
        if (date == null)
            return null;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * Adds months to a {@link Date}.
     * You can subtract by passing a negative number.
     * @param date The date to add months to.
     * @param months The number of months to add.
     * @return The new {@link Date}.
     */
    public static Date addMonths(Date date, int months) {
        if (date == null)
            return null;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * Adds days to a {@link Date}.
     * You can subtract by passing a negative number.
     * @param date The date to add days to.
     * @param days The number of days to add.
     * @return The new {@link Date}.
     */
    public static Date addDays(Date date, int days) {
        if (date == null)
            return null;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * Adds hours to a {@link Date}.
     * You can subtract by passing a negative number.
     * @param date The date to add hours to.
     * @param hours The number of hours to add.
     * @return The new {@link Date}.
     */
    public static Date addHours(Date date, int hours) {
        if (date == null)
            return null;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    /**
     * Adds minutes to a {@link Date}.
     * You can subtract by passing a negative number.
     * @param date The date to add minutes to.
     * @param minutes The number of minutes to add.
     * @return The new {@link Date}.
     */
    public static Date addMinutes(Date date, int minutes) {
        if (date == null)
            return null;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * Adds seconds to a {@link Date}.
     * You can subtract by passing a negative number.
     * @param date The date to add seconds to.
     * @param seconds The number of seconds to add.
     * @return The new {@link Date}.
     */
    public static Date addSeconds(Date date, int seconds) {
        if (date == null)
            return null;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * Adds millis to a {@link Date}.
     * You can subtract by passing a negative number.
     * @param date The date to add millis to.
     * @param millis The number of millis to add.
     * @return The new {@link Date}.
     */
    public static Date addMillis(Date date, int millis) {
        if (date == null)
            return null;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        calendar.add(Calendar.MILLISECOND, millis);
        return calendar.getTime();
    }

    /**
     * Gets the year of a {@link Date}.
     * @param date The {@link Date} to get the year of.
     * @return The year of the date.
     */
    public static int getYear(Date date) {
        if (date == null)
            return -1;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Gets the month of a {@link Date}.
     * @param date The {@link Date} to get the month of.
     * @return The month of the date.
     */
    public static int getMonth(Date date) {
        if (date == null)
            return -1;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Gets the days of a {@link Date}.
     * @param date The {@link Date} to get the days of.
     * @return The days of the date.
     */
    public static int getDay(Date date) {
        if (date == null)
            return -1;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Gets the hours of a {@link Date}.
     * @param date The {@link Date} to get the s of.
     * @return The hours of the date.
     */
    public static int getHours(Date date) {
        if (date == null)
            return -1;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Gets the minutes of a {@link Date}.
     * @param date The {@link Date} to get the minutes of.
     * @return The minutes of the date.
     */
    public static int getMinutes(Date date) {
        if (date == null)
            return -1;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * Gets the seconds of a {@link Date}.
     * @param date The {@link Date} to get the seconds of.
     * @return The seconds of the date.
     */
    public static int getSeconds(Date date) {
        if (date == null)
            return -1;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * Gets the millis of a {@link Date}.
     * @param date The {@link Date} to get the millis of.
     * @return The millis of the date.
     */
    public static int getMillis(Date date) {
        if (date == null)
            return -1;
        GregorianCalendar calendar = convertToGregorianCalendar(date);
        return calendar.get(Calendar.MILLISECOND);
    }

    /**
     * Generates a {@link Date} with the given year, month and day.
     * @param year The year to set.
     * @param month The month to set.
     * @param day The day to set.
     * @return The generated {@link Date}.
     */
    public static Date createDate(int year, int month, int day) {
        return createDate(year, month, day, 0, 0, 0, 0);
    }

    /**
     * Generates a {@link Date} with the given year, month and day.
     * @param year The year to set.
     * @param month The month to set.
     * @param day The day to set.
     * @return The generated {@link Date}.
     */
    public static Date createDate(int year, int month, int day, int hour, int minute, int second, int millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millis);
        return calendar.getTime();
    }

    /**
     * Makes sure that the millis are shown on an ISO instant string.
     * @param dateString The date string to enforce millis on.
     * @return The string with millis enforced.
     */
    private static String enforceMillisOnInstantString(String dateString) {
        if (!dateString.contains(".")) {
            dateString = dateString.substring(0, dateString.length() - 1) + ".000Z";
        } else {
            // If we have milliseconds, make sure they are of 3 digits length at most
            dateString = dateString.substring(0, ISO_INSTANT_LENGTH_WITHOUT_MILLIS) + "Z";
        }

        return dateString;
    }
}
