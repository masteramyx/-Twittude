package com.example.twittude.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope.weight
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.twittude.ui.TwitHistoryViewModel

@ExperimentalFoundationApi
@Composable
fun HistoryContent(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    viewModel: TwitHistoryViewModel,
) {
    viewModel.readTweetFromDisk()
    when (val state = viewModel.stateSubject
        .subscribeAsState(initial = TwitHistoryViewModel.State.Empty())
        .value) {
        is TwitHistoryViewModel.State.Data -> {
            Surface(modifier = modifier) {
                Stack(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.gravity(Alignment.Center)) {
                        Text(text = state.tweetList.first())
                    }
                    HistoryTopBar(
                        onNavIconPressed = onNavIconPressed
                    )
                }
            }
        }
        is TwitHistoryViewModel.State.Empty -> {
            Surface(modifier = modifier) {
                Stack(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.gravity(Alignment.Center)
                    ) {
                        Text(text = "NOTHING SAVED")
                    }
                    HistoryTopBar(
                        onNavIconPressed = onNavIconPressed
                    )
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun HistoryTopBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = {},
) {
    TwittudeAppBar(
        modifier = modifier,
        onNavIconPressed = onNavIconPressed,
        title = {
            Column(
                modifier = Modifier.weight(1f),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                Text(
                    "Saved History",
                    style = MaterialTheme.typography.subtitle1
                )
            }
        },
        actions = {}
    )
}