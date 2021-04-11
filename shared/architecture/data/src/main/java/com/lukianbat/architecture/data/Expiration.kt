package com.lukianbat.architecture.data

import java.util.concurrent.TimeUnit

sealed class Expiration {
    object Never: Expiration()
    data class Timed(val duration: Long, val unit: TimeUnit): Expiration()
}