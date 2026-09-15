package org.example.project.commons

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import org.koin.core.module.Module

interface DataStorePreferencesModule{

    val createDataStore: DataStore<Preferences>

}

expect fun provideDataStorePref(): Module