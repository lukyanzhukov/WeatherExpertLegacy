package com.lukianbat.core.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import androidx.annotation.Nullable;

import com.lukianbat.core.RussianLocale;

public final class DateFormatter {

    private final String formatString;

    public static DateFormatter withFormat(String format) {
        return new DateFormatter(format);
    }

    private DateFormatter(String formatString) {
        this.formatString = formatString;
    }

    public String format(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString, RussianLocale.get());
        return simpleDateFormat.format(date);
    }

    public String formatAtTimezone(Date date, TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString, RussianLocale.get());
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(date);
    }

    public Date parse(String formatDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString, RussianLocale.get());
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(formatDate);
    }

    /**
     * Returns null if date string is empty or parsing fails, otherwise works as parse().
     */
    @Nullable
    public Date parseOrNull(String date) {
        try {
            if (date == null || date.isEmpty()) {
                return null;
            } else {
                return parse(date);
            }
        } catch (ParseException e) {
            return null;
        }
    }

    public String getFormatString() {
        return formatString;
    }
}
