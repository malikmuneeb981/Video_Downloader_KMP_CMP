package org.example.project.commons

import androidx.compose.ui.graphics.ImageBitmap

expect suspend fun loadVideoThumbnail(urlOrPath: String): ImageBitmap?
