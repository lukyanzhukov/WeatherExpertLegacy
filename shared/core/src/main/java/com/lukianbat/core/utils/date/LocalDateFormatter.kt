package com.lukianbat.core.utils.date

import com.lukianbat.core.RussianLocale
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.format.ResolverStyle

class LocalDateFormatter @JvmOverloads constructor(formatString: String, strict: Boolean = false) {
    private val formatter = DateTimeFormatter.ofPattern(formatString, RussianLocale.get()).run {
        if (strict) withResolverStyle(ResolverStyle.STRICT) else this
    }

    fun format(date: LocalDate): String = formatter.format(date)
    fun format(date: LocalDateTime): String = formatter.format(date)

    @Throws(DateTimeParseException::class)
    fun parse(formatDate: String): LocalDate = formatter.parse(formatDate, LocalDate::from)

    fun parseOrNull(text: String): LocalDate? = try {
        formatter.parse(text, LocalDate::from)
    } catch (ex: DateTimeParseException) {
        null
    }
}
