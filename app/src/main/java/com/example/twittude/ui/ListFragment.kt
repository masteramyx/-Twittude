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
import com.example.twittude.views.ListContent
import org.koin.core.context.GlobalContext.get

class ListFragment : Fragment() {

    private val viewModel: TwitMainViewModel = get().koin.get()


    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getToken()
        viewModel.launchCounter()
        return ComposeView(context = requireContext()).apply {
            setContent {
                TwittudeTheme {
                    ListContent(
                        onNavIconPressed = {
                            (activity as NavActivity).openDrawer()
                        },
                        viewModel = viewModel
                    )
                }
            }
        }
    }

}