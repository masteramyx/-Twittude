package com.example.twittude.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.twittude.model.TwitListItem
import com.example.twittude.ui.TwitMainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@FlowPreview
@Composable
@ExperimentalCoroutinesApi
fun ListView(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    viewModel: TwitMainViewModel
) {

    val state: TwitMainViewModel.State = viewModel.stateChannel
        .openSubscription()
        .receiveAsFlow()
        .collectAsState(initial = TwitMainViewModel.State.Data(emptyList())).value
    if (state is TwitMainViewModel.State.Data) {
        if (state.data.isEmpty()) {
            Box(modifier = modifier) {
                SearchTopBar(
                    viewModel = viewModel,
                    onNavIconPressed = onNavIconPressed
                )
            }
        } else {
            Box(modifier = modifier) {
                Column {
                    SearchTopBar(viewModel = viewModel, onNavIconPressed = onNavIconPressed)
                    ListItemView(tweets = state.data, viewModel = viewModel)
                }
            }
        }
    }
}


@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    viewModel: TwitMainViewModel
) {

    val queryChannel = remember { Channel<String>(Channel.CONFLATED) }
    val text = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(viewModel) {
        queryChannel.receiveAsFlow()
            .debounce(1000)
            .filterNot {
                it.isEmpty()
            }
            .collect { query ->
                viewModel.search(query)
            }
    }
    TwittudeAppBar(
        modifier = modifier,
        onNavIconPressed = onNavIconPressed,
        title = {
            Column(modifier = Modifier) {
                BasicTextField(value = text.value, onValueChange = { newText ->
                    text.value = newText
                    queryChannel.offer(newText)
                })
            }
        }
    ) {
//
    }
}


@ExperimentalCoroutinesApi
@Composable
@Suppress("DEPRECATION")
fun ListItemView(tweets: List<TwitListItem>, viewModel: TwitMainViewModel) {
    LazyColumnForIndexed(items = tweets,
        modifier = Modifier.fillMaxSize(),
        itemContent = { _, item ->
            TweetListItem(item = item, viewModel = viewModel)
        })
}


@ExperimentalCoroutinesApi
@Composable
fun TweetListItem(item: TwitListItem, viewModel: TwitMainViewModel) {
    Timber.d(viewModel.toString())
    Row(modifier = Modifier.fillMaxSize()) {
        BasicText(text = item.text)
    }
}