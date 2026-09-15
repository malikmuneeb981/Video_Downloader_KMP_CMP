package com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels

data class ClipboardContent(
    val text: String,
    val isUrl: Boolean
)

//fun getClipboardContent(context: Context): ClipboardContent? {
//    val clipboard =
//        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//
//    val text = clipboard.primaryClip
//        ?.getItemAt(0)
//        ?.coerceToText(context)
//        ?.toString()
//        ?: return null
//
//    return ClipboardContent(
//        text = text,
//        isUrl = Patterns.WEB_URL.matcher(text).matches()
//    )
//}