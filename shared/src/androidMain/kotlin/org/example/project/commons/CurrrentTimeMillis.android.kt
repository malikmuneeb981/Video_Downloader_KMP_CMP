package org.example.project.commons

actual fun getCurrentTimeMillis(): Long {
    return System.currentTimeMillis()
}