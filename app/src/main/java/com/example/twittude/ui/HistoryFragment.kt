package com.example.twittude.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.twittude.NavActivity
import com.example.twittude.theme.TwittudeTheme
import com.example.twittude.views.HistoryContent
import org.koin.core.context.GlobalContext.get

class HistoryFragment : Fragment() {

    private val viewModel: TwitHistoryViewModel = get().koin.get()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(context = requireContext()).apply {
            setContent {
                TwittudeTheme() {
                    HistoryContent(viewModel = viewModel,
                        onNavIconPressed = {
                            (activity as NavActivity).openDrawer()
                        })
                }
            }
        }
    }

}