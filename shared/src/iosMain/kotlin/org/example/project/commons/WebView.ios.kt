package org.example.project.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(url: String,modifier: Modifier) {
    UIKitView(
        factory = {
            val config = WKWebViewConfiguration()
            val webView = WKWebView(frame = CGRectMake(0.0, 0.0, 0.0, 0.0), configuration = config)
            val request = NSURLRequest(NSURL(string = url))
            webView.loadRequest(request)
            webView
        },
        modifier = modifier
    )
}