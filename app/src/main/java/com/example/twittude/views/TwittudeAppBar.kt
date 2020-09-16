package com.example.twittude.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.twittude.R
import com.example.twittude.theme.elevatedSurface

@Composable
fun TwittudeAppBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column {
        val bgColor = MaterialTheme.colors.elevatedSurface(3.dp)
        TopAppBar(
            modifier = modifier,
            backgroundColor = bgColor.copy(alpha = 0.95f),
            elevation = 2.dp,
            contentColor = MaterialTheme.colors.onSurface,
            actions = actions,
            title = title,
            navigationIcon = {
                Image(
                    asset = vectorResource(id = R.drawable.ic_ai),
                    modifier = Modifier
                        .gravity(Alignment.CenterHorizontally)
                        .clickable(onClick = onNavIconPressed)
                        .padding(horizontal = 16.dp)
                )
            }
        )
    }
}