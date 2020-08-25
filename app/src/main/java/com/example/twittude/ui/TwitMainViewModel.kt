package com.example.twittude.ui

import com.example.twittude.api.TwitMainRepository
import com.karakum.base.BaseViewModel
import com.karakum.base.Mvvm
import com.karakum.base.scheduler
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.launch
import twitter4j.QueryResult
import twitter4j.Twitter

class TwitMainViewModel(
    val repository: TwitMainRepository,
    val scheduler: scheduler
) : BaseViewModel<TwitMainViewModel.State>() {


    fun getToken() {
        lifecycleScope.launch {
            try {
                val twitter = repository.getTwitterAuthentication()
                stateSubject.onNext(State.Authentication(twitter))
            } catch (exception: Exception) {
                stateSubject.onNext(State.Error(exception))
            }
        }
    }

    fun search(queryString: String) {
        lifecycleScope.launch {
            try {
                val result = repository.searchQuery(queryString)
                stateSubject.onNext(State.Data(result))
            } catch (exception: Exception) {
                stateSubject.onNext(State.Error(exception))
            }
        }
    }


    sealed class State : Mvvm.State {

        @Parcelize
        data class Authentication(
            val twitter: Twitter
        ) : State()

        @Parcelize
        data class Data(val data: QueryResult) : State()

        @Parcelize
        data class Error(val error: Throwable) : State()
    }
}