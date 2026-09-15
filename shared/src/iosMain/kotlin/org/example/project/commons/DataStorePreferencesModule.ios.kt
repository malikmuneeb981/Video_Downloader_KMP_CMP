package org.example.project.commons

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

class DataStorePreferencesModuleIOS:DataStorePreferencesModule {

        @OptIn(ExperimentalForeignApi::class)
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        val path = requireNotNull(documentDirectory).path + "/my_settings.preferences_pb"
        override val createDataStore: DataStore<Preferences>
        get() = PreferenceDataStoreFactory.createWithPath {
            path.toPath()
        }

}

actual fun provideDataStorePref(): Module {
    return module {
        single<DataStorePreferencesModule> {
            DataStorePreferencesModuleIOS()
        }
        single<org.example.project.utils.DataStorePreferences> {
            org.example.project.utils.DataStorePreferences(get())
        }
    }
}