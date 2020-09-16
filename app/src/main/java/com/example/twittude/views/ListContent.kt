package com.example.twittude.views

import androidx.compose.foundation.BaseTextField
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.weight
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.twittude.model.TwitListItem
import com.example.twittude.ui.TwitMainViewModel


/**
 * Entry for the list of tweets returned from search or for the empty state user experiences prior to making a search
 */
@ExperimentalFoundationApi
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    viewModel: TwitMainViewModel,
) {
    val (searchTerm, updateSearchTerm) = remember { mutableStateOf(TextFieldValue("")) }
    viewModel.search(searchTerm.text)
    when (val state = viewModel.stateSubject
        .subscribeAsState(initial = TwitMainViewModel.State.Empty())
        .value) {
        is TwitMainViewModel.State.Data -> {
            Surface(modifier = modifier) {
                Stack(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        ListItemView(tweets = state.data.tweets.map {
                            TwitListItem(
                                it.text
                            )
                        })
                    }
                    TopBar(
                        onNavIconPressed = onNavIconPressed,
                        search = true,
                        searchTerm = searchTerm,
                        updateSearchTerm = updateSearchTerm
                    )
                }
            }
        }

        is TwitMainViewModel.State.Empty -> {
            Surface(modifier = modifier) {
                Stack(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.gravity(Alignment.Center)
                    ) {
                        //ListItemView(tweets = state.data.tweets.map { TwitListItem(it.text) })
                        Text(
                            text = "Please Enter a search term",
                            modifier = Modifier
                        )
                    }
                    TopBar(
                        onNavIconPressed = onNavIconPressed,
                        search = true,
                        searchTerm = searchTerm,
                        updateSearchTerm = updateSearchTerm
                    )
                }
            }
        }
        else -> {
            Surface(modifier = modifier) {
                Stack(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.gravity(Alignment.Center)
                    ) {
                        //ListItemView(tweets = state.data.tweets.map { TwitListItem(it.text) })
                        Text(
                            text = "Please Enter a search term",
                            modifier = Modifier
                        )
                    }
                    TopBar(
                        onNavIconPressed = onNavIconPressed,
                        search = true,
                        searchTerm = searchTerm,
                        updateSearchTerm = updateSearchTerm
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = {},
    search: Boolean,
    searchTerm: TextFieldValue,
    updateSearchTerm: (TextFieldValue) -> Unit,
) {
    TwittudeAppBar(
        modifier = modifier,
        onNavIconPressed = onNavIconPressed,
        title = {
            Column(
                modifier = Modifier.weight(1f),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                if (!search) {
                    Text(
                        "Test",
                        style = MaterialTheme.typography.subtitle1
                    )
                } else {
                    BaseTextField(value = searchTerm, onValueChange = updateSearchTerm)
                }
            }
        },
        actions = {
            ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                // Search icon
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
                        .preferredHeight(24.dp)
                ) {
                    Icon(Icons.Outlined.Search)
                }
//                Icon(
//                    asset = Icons.Outlined.Search,
//                    modifier = Modifier
//                        .clickable(onClick = {
//                            TopBar(search = true)
//                        }) // TODO: Show not implemented dialog.
//                        .padding(horizontal = 12.dp, vertical = 16.dp)
//                        .preferredHeight(24.dp)
//                )
            }
        }
    )
}

@Composable
fun searchView() {
    Column {
        Text("Search")
    }
}


@Composable
fun ListItemView(tweets: List<TwitListItem>) {
    LazyColumnForIndexed(items = tweets,
        modifier = Modifier.fillMaxSize()
            .weight(1f),
        itemContent = { index, item ->
            if (index == 0) {
                Spacer(modifier = Modifier.preferredHeight(64.dp))
            } else {
                ListItem(item)

                Divider()
            }
        })
}


@Composable
fun ListItem(item: TwitListItem) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = item.text)
    }
}

