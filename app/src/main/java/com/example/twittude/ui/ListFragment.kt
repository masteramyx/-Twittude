package com.example.twittude.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import com.example.twittude.NavActivity
import com.example.twittude.theme.TwittudeTheme
import com.example.twittude.views.ListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext.get
import org.koin.core.parameter.parametersOf

class ListFragment : Fragment() {

    @ExperimentalCoroutinesApi
    private val viewModel: TwitMainViewModel = get().get() {
        parametersOf(SavedStateHandle())
    }


    @ExperimentalCoroutinesApi
    @ExperimentalFoundationApi
    @FlowPreview
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CoroutineScope(IO).launch {
            viewModel.getToken()
        }
        return ComposeView(context = requireContext()).apply {
            setContent {
                TwittudeTheme {
                    ListView(
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