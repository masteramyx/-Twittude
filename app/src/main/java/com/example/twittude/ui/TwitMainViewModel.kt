package com.example.twittude.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import com.example.twittude.api.TwitMainRepository
import com.example.twittude.model.TwitListItem
import com.karakum.base.Mvvm
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class TwitMainViewModel(
    val savedStateHandle: SavedStateHandle,
    val repository: TwitMainRepository
) : TwitBaseViewModel<TwitMainViewModel.State>(), LifecycleObserver {


    var searchFor: String = ""


    suspend fun getToken() {
        repository.checkCacheForBearer()
    }


    suspend fun search(queryString: String) {
        Timber.d("QUERY STRING: $queryString")
        searchFor = queryString
        val results = repository.searchQuery(queryString = queryString)
        stateChannel.send(State.Data(results))
    }

    fun saveTweetToDisk(tweet: TwitListItem) {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.saveTweetToDisk(tweet = tweet.text)
        }
    }

    sealed class State : Mvvm.State {
        @Parcelize
        data class Data(val data: List<TwitListItem>) : State()
    }
}
