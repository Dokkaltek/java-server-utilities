package com.github.dokkaltek.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for date formats.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateFormatConstants {
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_MILLIS_FORMAT = "HH:mm:ss.SSS";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_MILLI_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
}
