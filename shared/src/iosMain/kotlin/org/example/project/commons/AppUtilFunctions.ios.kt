package org.example.project.commons

import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSURL
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.popoverPresentationController

class AppUtilFunctionsIOS:AppUtilFunctions {
    override fun shareText(text: String, title: String) {
        val activityViewController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )

        // 2. Get the root view controller of the app to present the share sheet
        val window = UIApplication.sharedApplication.keyWindow
        val rootViewController = window?.rootViewController

        // 3. Prevent crash on iPad (iPads require a source view for popovers)
        activityViewController.popoverPresentationController?.sourceView = rootViewController?.view

        // 4. Present the share sheet
        rootViewController?.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null
        )
    }
    override fun openLink(url: String) {
        try {
            // 1. Ensure the URL has a scheme, otherwise iOS won't know how to open it
            val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                "https://$url"
            } else {
                url
            }
            // 2. Safely create the NSURL. If it's null (e.g. malformed URL), we return early
            val nsUrl = NSURL(string = formattedUrl) ?: return
            // 3. Check if we can open it
            if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
                // 4. Use the modern iOS 10+ API to open the URL
                UIApplication.sharedApplication.openURL(
                    url = nsUrl,
                    options = emptyMap<Any?, Any>(),
                    completionHandler = null
                )
            } else {
                println("Cannot open URL: $formattedUrl")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun changeAppLanguage(languageCode: String) {
        // 1. Override the AppleLanguages key in the app's user defaults
        NSUserDefaults.standardUserDefaults.setObject(
            arrayListOf(languageCode),
            "AppleLanguages"
        )
        // 2. Force the defaults to save immediately
        NSUserDefaults.standardUserDefaults.synchronize()
    }
}
actual fun provideAppUtilFunctionsModule(): Module {
    return module {
        single<AppUtilFunctions>{
            AppUtilFunctionsIOS()
        }
    }
}