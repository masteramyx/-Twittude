package com.example.twittude

import com.karakum.base.BaseViewModel
import com.karakum.base.Mvvm
import kotlinx.android.parcel.Parcelize

class TwitMainViewModel : BaseViewModel<TwitMainViewModel.State>() {

    sealed class State : Mvvm.State {

        @Parcelize
        data class Data(val temp : Boolean = true) : State()
    }
}