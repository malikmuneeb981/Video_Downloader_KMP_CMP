package org.example.project.commons

import kotlin.time.Clock

actual fun getCurrentTimeMillis(): Long {
    return Clock.System.now().toEpochMilliseconds()
}