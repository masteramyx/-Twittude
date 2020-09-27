package com.example.twittude.ui

import com.example.twittude.api.TwitMainRepository
import com.example.twittude.model.TwitListItem
import com.karakum.base.BaseViewModel
import com.karakum.base.Mvvm
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import twitter4j.QueryResult
import twitter4j.Twitter

class TwitMainViewModel(
    val repository: TwitMainRepository
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
                if (queryString.isEmpty()) {
                    stateSubject.onNext(State.Empty())
                } else {
                    queryString.apply {
                        delay(500)
                        val result = repository.searchQuery(queryString)
                        stateSubject.onNext(State.Data(result))
                    }
                }
            } catch (exception: Exception) {
                stateSubject.onNext(State.Error(exception))
            }
        }
    }

    fun launchCounter() {
        lifecycleScope.launch {
            repository.incrementCounter()
        }
    }

    fun saveTweetToDisk(tweet: TwitListItem) {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.saveTweetToDisk(tweet = tweet.text)
        }
    }

    sealed class State : Mvvm.State {

        @Parcelize
        data class Authentication(
            val twitter: Twitter
        ) : State()

        @Parcelize
        data class Data(val data: QueryResult) : State()

//        @Parcelize
//        data class Data(val data: List<TwitListItem>) : State()

        @Parcelize
        data class Empty(val empty: Boolean = true) : State()

        @Parcelize
        data class Error(val error: Throwable) : State()
    }
}

val fakeList = listOf(
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME"),
    TwitListItem("COW JUMPED OVER THE MOON"),
    TwitListItem("FOX IN THE SNOW"),
    TwitListItem("TWO SIGMA HERE I COME")
)

val fakeList2 = listOf(
    TwitListItem("CTest Search"),
    TwitListItem("Test Search"),
    TwitListItem("TTest Search"),
    TwitListItem("CTest Search"),
    TwitListItem("Test Search"),
    TwitListItem("TTest Search"),
    TwitListItem("CTest Search"),
    TwitListItem("Test Search"),

    )