package org.example.project.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun ReelsView(
    url: String,
    isPlaying: Boolean,
    isMuted: Boolean,
    modifier: Modifier = Modifier,
    onBufferingStateChanged: (Boolean) -> Unit = {}
)