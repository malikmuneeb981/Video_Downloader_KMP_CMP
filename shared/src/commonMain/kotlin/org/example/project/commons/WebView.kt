package org.example.project.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(url: String,modifier: Modifier)