package org.example.project.commons

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.core.net.toUri
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.Locale

class AppUtilFunctionsAndroid(val context: Context):AppUtilFunctions {
    override fun shareText(text: String, title: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        val chooserIntent = Intent.createChooser(intent, title).apply {
            // Add this flag to the CHOOSER intent as well
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooserIntent, )
    }
    override fun openLink(url:String){
        try {

            val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {

        }
    }

    override fun changeAppLanguage(languageCode: String){
        val locale = Locale.forLanguageTag(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}

actual fun provideAppUtilFunctionsModule(): Module {
    return module {
        single<AppUtilFunctions>{
            AppUtilFunctionsAndroid(get())
        }
    }
}