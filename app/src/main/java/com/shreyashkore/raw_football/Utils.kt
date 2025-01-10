package com.shreyashkore.raw_football

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

inline fun <T> tryOrNull(block: () -> T) = try {
    block()
} catch (_: Throwable) {
    null
}


fun Instant.toLocal(): LocalDateTime = atZone(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDateTime.format(
    pattern: String
) : String = format(DateTimeFormatter.ofPattern(pattern))