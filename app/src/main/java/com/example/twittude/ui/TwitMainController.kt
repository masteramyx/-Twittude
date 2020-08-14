package com.example.twittude.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twittude.R
import com.example.twittude.model.TwitListItem
import com.example.twittude.model.TwitListSearchItem
import com.example.twittude.ui.adapter.TwitMainSearchListItemViewHolder
import com.google.android.material.snackbar.Snackbar
import com.karakum.base.BaseMvvmController
import com.karakum.base.Mvvm
import com.karakum.base.recycler.BaseRecyclerAdapterImpl
import com.karakum.base.recycler.BaseRecyclerItem
import com.karakum.base.recycler.BaseRecyclerViewHolder
import kotlinx.android.synthetic.main.twit_main_controller.view.*
import org.koin.core.context.GlobalContext.get
import timber.log.Timber

class TwitMainController : BaseMvvmController<TwitMainViewModel, TwitMainViewModel.State>(),
    TwitMainSearchListItemViewHolder.SearchListener {

    override val viewModel: TwitMainViewModel = get().koin.get()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        object : BaseRecyclerAdapterImpl<BaseRecyclerItem>(context) {
            override fun onBindViewHolderListeners(
                item: BaseRecyclerItem,
                holder: BaseRecyclerViewHolder<*>
            ) {
                when (item) {
                    is TwitListSearchItem -> {
                        (holder as TwitMainSearchListItemViewHolder).listener =
                            this@TwitMainController
                    }
                }
            }
        }
    }

    private val searchView by lazy(LazyThreadSafetyMode.NONE) {
        TwitListSearchItem()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.twit_main_controller, container, false).apply {
            twitMainRecycler.adapter = adapter
            twitMainRecycler.layoutManager = LinearLayoutManager(activity)
            adapter.addItem(searchView, false)
        }
    }


    override fun onAttach(view: View) {
        super.onAttach(view)
        viewModel.getToken()
    }

    override fun onSearched(query: String) {
        viewModel.search(query)
    }

    override fun onStateChange(state: Mvvm.State) {
        when (state) {
            is TwitMainViewModel.State.Authentication -> {
                Snackbar.make(view!!, "Authenticated", Snackbar.LENGTH_SHORT).show()
                //Set search bar listener after authentication
            }
            is TwitMainViewModel.State.Data -> {
                Timber.d(state.data.toString())
                val list = mutableListOf<BaseRecyclerItem>()
                list.add(searchView)
                state.data.tweets.forEach {
                    list.add(TwitListItem(it.text))
                }
                adapter.addItems(list)
            }
            is TwitMainViewModel.State.Error -> {
                Timber.e(state.error)
            }
        }
    }
}