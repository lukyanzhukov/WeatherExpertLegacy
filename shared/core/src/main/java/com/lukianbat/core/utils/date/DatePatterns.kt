package com.lukianbat.core.utils.date

object DatePatterns {

    /**
     * Example: 3 июля
     */
    const val DAY_OF_MONTH = "d MMMM"

    /**
     * Example: 3 июля 2016
     */
    const val DAY_OF_MONTH_WITH_YEAR = "d MMMM yyyy"

    /**
     * Example: 03.07.2016
     */
    @Deprecated("Use NUMERIC_DATE_2", ReplaceWith("NUMERIC_DATE_2"))
    const val NUMERIC_DATE = "dd.MM.yyyy"
    const val NUMERIC_DATE_2 = "dd.MM.uuuu"

    /**
     * Example: 2018-01-25
     */
    const val NUMERIC_DATE_REVERSE_DASHES = "yyyy-MM-dd"

    /**
     * Example: 03.07.2016 18:00
     */
    const val NUMERIC_DATE_TIME = "dd.MM.yyyy HH:mm"

    /**
     * Example: 03.07.2016, 18:00
     */
    const val NUMERIC_DATE_TIME_COMMA = "dd.MM.yyyy, HH:mm"

    /**
     * Example: 03 января 2016 18:00
     */
    const val NUMERIC_DATE_TIME_COMMA_MONTH = "dd MMMM yyyy HH:mm"

    /**
     * Example: 03_07_2016_18_00_23
     */
    const val NUMERIC_DATE_TIME_UNDERSCORE = "dd_MM_yyyy_HH_mm_ss"

    /**
     * Example: 2018-01-25T13:45:53.312+03:00
     */
    const val NUMERIC_DATE_TIME_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    /**
     * Example: 2018-01-25T13:45:53
     */
    const val NUMERIC_DATE_TIME_NO_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss"

    /**
     * Example: 2018-01-25T13:45:53.823
     */
    const val NUMERIC_DATE_TIME_NO_TIMEZONE_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSS"

    /**
     * Example: 11/19
     */
    const val CARD_EXPIRY_DATE = "MM/yy"

    /**
     * Example: 2019-11
     */
    const val PROXY_DESS_CARD_EXPIRY_DATE = "yyyy-MM"

    /**
     * Example: 12 23 15:16:56
     */
    const val LOG_DATE_FORMAT = "MM dd HH:mm:ss"
}