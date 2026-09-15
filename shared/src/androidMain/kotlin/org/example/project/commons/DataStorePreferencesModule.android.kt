package org.example.project.commons

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import okio.Path.Companion.toPath
import org.example.project.utils.DataStorePreferences
import org.koin.core.module.Module
import org.koin.dsl.module

class DataStorePreferencesModuleAndroid(context: Context):DataStorePreferencesModule {
    val path = context.filesDir.resolve("app_settings.preferences_pb").absolutePath
    override val createDataStore: DataStore<Preferences>
        get() = PreferenceDataStoreFactory.createWithPath {
            path.toPath()
        }


}

actual fun provideDataStorePref(): Module {
    return module {
        single<DataStorePreferencesModule> {
            DataStorePreferencesModuleAndroid(get())
        }
        single<DataStorePreferences> {
            DataStorePreferences(get())
        }
    }
}