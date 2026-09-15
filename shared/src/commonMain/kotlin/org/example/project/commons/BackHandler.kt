package org.example.project.commons

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(onBackPressed:()-> Unit)