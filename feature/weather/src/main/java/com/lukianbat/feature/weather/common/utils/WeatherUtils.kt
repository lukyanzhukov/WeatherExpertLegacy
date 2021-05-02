package com.lukianbat.feature.weather.common.utils

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

private const val DATE_PATTERN = "eee, d MMM"

fun Instant.toFormatString(): String = DateTimeFormatter.ofPattern(DATE_PATTERN)
    .withZone(ZoneId.from(ZoneOffset.UTC))
    .format(this)
