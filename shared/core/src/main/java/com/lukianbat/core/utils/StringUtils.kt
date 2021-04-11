package com.lukianbat.core.utils

import com.lukianbat.core.RussianLocale

object StringUtils {
    private const val TRIMMED_PHONE_LENGTH = 10
    private const val RUSSIA_COUNTRY_CODE = "7"
    private const val SECOND_LETTER = 1

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }

    /**
     * Return empty string if the input parameter is null, otherwise
     * return the parameter itself.
     *
     * @param string nullable string
     * @return non-null string
     */
    fun toNonNullString(string: String?): String {
        return string ?: ""
    }

    fun toNullIfEmpty(string: String?): String? {
        return if (string == null || string.isEmpty()) {
            null
        } else string
    }

    //from org.apache.commons.lang3.StringUtils.containsIgnoreCase()
    fun containsIgnoreCase(str: String?, searchStr: String?): Boolean {
        if (str == null || searchStr == null) {
            return false
        }
        val length = searchStr.length
        if (length == 0) {
            return true
        }
        for (i in str.length - length downTo 0) {
            if (str.regionMatches(i, searchStr, 0, length, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    fun joinNotEmpty(delimiter: CharSequence?, tokens: Collection<String>): String {
        return joinNotEmpty(delimiter, *tokens.toTypedArray())
    }

    fun joinNotEmpty(delimiter: CharSequence?, vararg tokens: String?): String {
        val sb = StringBuilder()
        for (token in tokens) {
            if (!isEmpty(token)) {
                if (sb.length > 0) {
                    sb.append(delimiter)
                }
                sb.append(token)
            }
        }
        return sb.toString()
    }

    fun onlyDigits(string: String): String {
        return string.replace("\\D".toRegex(), "")
    }

    fun filterDigitsAndParseInt(string: String): Int {
        return if (string.isEmpty()) {
            0
        } else {
            val newString = string.replace("[^\\d]".toRegex(), "")
            newString.toInt()
        }
    }

    /**
     * Converts phone in format +7 (123) 456 78-90 to 1234567890
     */
    fun phoneWithoutCountryCodeAndNonDigitSymbols(phone: String): String {
        val digits = onlyDigits(phone)
        return if (digits.length <= TRIMMED_PHONE_LENGTH) {
            if (!digits.isEmpty() && digits.substring(0, SECOND_LETTER) == RUSSIA_COUNTRY_CODE) {
                digits.substring(SECOND_LETTER)
            } else {
                digits
            }
        } else digits.substring(SECOND_LETTER)
    }

    /**
     * Examples: 0 -> 0:00, 500 -> 8:20, 4028 -> 67:08
     * Result with negative values is undefined
     */
    fun formatAsTime(time: Int): String {
        val seconds = time % 60
        val minutes = time / 60
        return String.format(RussianLocale.get(), "%d:%02d", minutes, seconds)
    }

    fun removeEnglishLetters(value: String): String {
        return value.replace("[a-zA-Z]".toRegex(), "")
    }
}
