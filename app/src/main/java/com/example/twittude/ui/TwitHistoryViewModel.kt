package com.example.twittude.ui

import com.example.twittude.api.TwitMainRepository
import com.karakum.base.BaseViewModel
import com.karakum.base.Mvvm
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TwitHistoryViewModel(
    val repository: TwitMainRepository
) : BaseViewModel<TwitHistoryViewModel.State>() {


    fun readTweetFromDisk() {
        lifecycleScope.launch {
            repository.readTweetFromDisk()
                .flowOn(Dispatchers.IO)
                .collect {
                    stateSubject.onNext(
                        State.Data(listOf(it))
                    )
                }
        }
    }


    sealed class State : Mvvm.State {

        @Parcelize
        data class Data(val tweetList: List<String>) : State()

        @Parcelize
        data class Empty(val empty: Boolean = true) : State()
    }

}