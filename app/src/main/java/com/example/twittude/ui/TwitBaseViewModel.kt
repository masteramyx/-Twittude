package com.example.twittude.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karakum.base.Mvvm
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

@ExperimentalCoroutinesApi
abstract class TwitBaseViewModel<S : Mvvm.State> : ViewModel() {


    val stateChannel: BroadcastChannel<S> = ConflatedBroadcastChannel()

    val loadingSubject: BroadcastChannel<Boolean> = ConflatedBroadcastChannel()

    val lifecycleScope = viewModelScope

    private var compositeDisposable: CompositeDisposable? = null

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        stateChannel.cancel()
    }
}