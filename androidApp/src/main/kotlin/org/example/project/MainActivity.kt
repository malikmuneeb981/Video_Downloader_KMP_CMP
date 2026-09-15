package org.example.project

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val platformModule = module {
            single<Context> { applicationContext }
            single<DownloadManager> {
                applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            }
        }
        setContent {
            App(platformModule = platformModule)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}