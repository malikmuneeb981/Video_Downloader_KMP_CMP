package org.example.project.commons

import org.koin.core.module.Module

interface AppUtilFunctions{
    fun shareText(text: String,title: String = "Share via")
    fun openLink(url:String)
    fun changeAppLanguage(languageCode: String)
}
expect fun provideAppUtilFunctionsModule(): Module