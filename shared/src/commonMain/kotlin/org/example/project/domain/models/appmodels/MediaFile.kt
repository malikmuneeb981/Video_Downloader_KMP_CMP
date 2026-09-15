package org.example.project.domain.models.appmodels


data class MediaFile(
    val uri: String,
    val path: String,
    val folderName: String,
    val name: String,
    val size: Long,
    val dateModified: Long,
    val thumbnail: String,
    val duration: Long
) {

//    // ✅ Size → KB / MB / GB
//    val readableSize: String
//        get() {
//            val kb = size / 1024.0
//            val mb = kb / 1024.0
//            val gb = mb / 1024.0
//
//            return when {
//                gb >= 1 -> String.format("%.2f GB", gb)
//                mb >= 1 -> String.format("%.2f MB", mb)
//                else -> String.format("%.2f KB", kb)
//            }
//        }
//
//    // ✅ Date → formatted
//    val formattedDate: String
//        get() {
//            val sdf = java.text.SimpleDateFormat(
//                "dd MMM yyyy, hh:mm a",
//                java.util.Locale.getDefault()
//            )
//            return sdf.format(java.util.Date(dateModified * 1000)) // remove *1000 if already ms
//        }
//
//    // ✅ Duration → hh:mm:ss
//    val formattedDuration: String
//        get() {
//            val totalSeconds = duration / 1000
//            val hours = totalSeconds / 3600
//            val minutes = (totalSeconds % 3600) / 60
//            val seconds = totalSeconds % 60
//
//            return if (hours > 0) {
//                String.format("%02d:%02d:%02d", hours, minutes, seconds)
//            } else {
//                String.format("%02d:%02d", minutes, seconds)
//            }
//        }
}