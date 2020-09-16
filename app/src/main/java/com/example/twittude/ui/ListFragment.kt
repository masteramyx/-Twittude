package com.example.twittude.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.twittude.views.ListContent
import com.example.twittude.NavActivity
import com.example.twittude.api.TwitMainRepositoryImpl
import com.example.twittude.model.TwitListItem
import com.example.twittude.theme.TwittudeTheme

class ListFragment : Fragment() {

    private val viewModel = TwitMainViewModel(TwitMainRepositoryImpl())
    lateinit var items: List<TwitListItem>


    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getToken()
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