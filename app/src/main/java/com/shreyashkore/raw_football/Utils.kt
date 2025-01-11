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

/**
 * Converts Instant to LocalDateTime
 */
fun Instant.toLocal(): LocalDateTime = atZone(ZoneId.systemDefault()).toLocalDateTime()

/**
 * Formats LocalDateTime to string
 */
fun LocalDateTime.format(
    pattern: String
) : String = format(DateTimeFormatter.ofPattern(pattern))