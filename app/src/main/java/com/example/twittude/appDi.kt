package com.example.twittude

import com.example.twittude.api.TwitMainRepository
import com.example.twittude.api.TwitMainRepositoryImpl
import com.example.twittude.ui.TwitMainViewModel
import com.karakum.IObservableSchedulerRx2
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appDi = module {

    factory {
        IObservableSchedulerRx2.SUBSCRIBE_IO_OBSERVE_ANDROID_MAIN
    }

    single<TwitMainRepository>{
        TwitMainRepositoryImpl()
    }

    viewModel {
        TwitMainViewModel(
            repository = get(),
            scheduler = get()
        )
    }
}