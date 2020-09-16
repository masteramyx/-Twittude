package com.example.twittude

import com.example.twittude.api.TwitMainRepository
import com.example.twittude.api.TwitMainRepositoryImpl
import com.example.twittude.ui.TwitMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appDi = module {

    single<TwitMainRepository> {
        TwitMainRepositoryImpl()
    }

    viewModel {
        TwitMainViewModel(
            repository = get()
        )
    }
}