package com.example.twittude.views

import androidx.compose.runtime.mutableStateListOf
import com.example.twittude.model.TwitListItem

class ListUiState(
    private val initialTweetList: List<TwitListItem>
) {
    private val _tweetList: MutableList<TwitListItem> =
        mutableStateListOf(*initialTweetList.toTypedArray())

    val tweetList = _tweetList
}