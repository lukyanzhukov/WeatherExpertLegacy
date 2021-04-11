package com.lukianbat.core.utils

import com.lukianbat.core.utils.date.DatePatterns
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.YearMonth
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

/**
 * Returns defensive copy of date, or null if date is null;
 */
fun Date?.copy() = if (this != null) Date(time) else null

fun Date.toLocalDateTime(): LocalDateTime =
    DateTimeUtils.toInstant(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDateTime.toDate(): Date {
    return DateTimeUtils.toDate(this.atZone(ZoneId.systemDefault()).toInstant())
}

fun date(year: Int, month: Int, day: Int): Date {
    return Calendar.getInstance().apply { set(year, month - 1, day, 0, 0) }.time
}

fun parseExpiryDate(text: String): YearMonth? = try {
    val formatter = DateTimeFormatter.ofPattern(DatePatterns.CARD_EXPIRY_DATE)
    YearMonth.parse(text, formatter)
} catch (exception: Exception) {
    null
}

fun YearMonth.formatAsExpiryDate(): String = this.format(DateTimeFormatter.ofPattern(DatePatterns.CARD_EXPIRY_DATE))

fun YearMonth.formatAsProxyDessExpiryDate(): String {
    return this.format(DateTimeFormatter.ofPattern(DatePatterns.PROXY_DESS_CARD_EXPIRY_DATE))
}

fun secondsBetween(first: Date, second: Date): Int {
    return ((second.time - first.time) / 1000).toInt()
}