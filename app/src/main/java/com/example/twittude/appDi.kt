package com.example.twittude

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import com.example.twittude.api.TwitMainRepository
import com.example.twittude.api.TwitMainRepositoryImpl
import com.example.twittude.ui.TwitHistoryViewModel
import com.example.twittude.ui.TwitMainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDi = module {

    single<TwitMainRepository> {
        TwitMainRepositoryImpl()
    }

    single<DataStore<Preferences>>(
        qualifier = named(name = "counter")
    ) {
        androidContext().createDataStore(
            name = "settings"
        )
    }

    single<DataStore<Preferences>>(
        qualifier = named(name = "tweets")
    ) {
        androidContext().createDataStore(
            name = "tweets"
        )
    }

    viewModel {
        TwitMainViewModel(
            repository = get()
        )
    }

    viewModel {
        TwitHistoryViewModel(
            repository = get()
        )
    }
}