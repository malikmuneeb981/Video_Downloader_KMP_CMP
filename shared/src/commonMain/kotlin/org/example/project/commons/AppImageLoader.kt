package org.example.project.commons

import coil3.ImageLoader
import coil3.PlatformContext

expect fun getPlatformImageLoader(context: PlatformContext): ImageLoader
