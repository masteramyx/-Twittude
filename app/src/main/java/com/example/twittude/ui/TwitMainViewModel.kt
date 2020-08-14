package com.example.twittude.ui

import com.example.twittude.api.TwitMainRepository
import com.karakum.base.BaseViewModel
import com.karakum.base.Mvvm
import com.karakum.base.scheduler
import com.karakum.base.subscribeBy
import kotlinx.android.parcel.Parcelize
import twitter4j.QueryResult
import twitter4j.Twitter

class TwitMainViewModel(
    val repository: TwitMainRepository,
    val scheduler: scheduler
) : BaseViewModel<TwitMainViewModel.State>() {

    private var twitter: Twitter? = null

    fun getToken() {
        addToDisposables(repository.getTwitterAuthentication()
            .compose(scheduler.scheduleSingle())
            .subscribe(
                {
                    twitter = it
                    stateSubject.onNext(
                        State.Authentication(
                            it
                        )
                    )
                },
                {
                    stateSubject.onNext(
                        State.Error(
                            it
                        )
                    )
                }
            ))
    }

    fun search(query: String){
         addToDisposables(repository.searchQuery(query)
            .compose(scheduler.scheduleSingle())
            .subscribeBy({
                stateSubject.onNext(
                    State.Data(
                        it
                    )
                )
            },{
                stateSubject.onNext(
                    State.Error(
                        it
                    )
                )
            }))
    }


    sealed class State : Mvvm.State {

        @Parcelize
        data class Authentication(val twitter: Twitter
        ) : State()

        @Parcelize
        data class Data(val data: QueryResult): State()

        @Parcelize
        data class Error(val error: Throwable) : State()
    }
}