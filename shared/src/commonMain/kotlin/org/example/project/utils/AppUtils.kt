package org.example.project.utils


fun formatDurationHMS(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60

    // padStart(2, '0') ensures the number is always at least 2 digits long
    val minStr = minutes.toString().padStart(2, '0')
    val secStr = secs.toString().padStart(2, '0')

    return if (hours > 0) {
        val hourStr = hours.toString().padStart(2, '0')
        "$hourStr:$minStr:$secStr"
    } else {
        "$minStr:$secStr"
    }
}