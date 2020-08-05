package com.example.twittude.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twittude.R
import com.example.twittude.ui.adapter.TwitMainListAdapter
import com.example.twittude.ui.adapter.TwitMainListItemViewHolder
import com.google.android.material.snackbar.Snackbar
import com.karakum.base.BaseMvvmController
import com.karakum.base.Mvvm
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.twit_main_controller.view.*
import org.koin.core.context.GlobalContext.get
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TwitMainController : BaseMvvmController<TwitMainViewModel, TwitMainViewModel.State>() {

    override val viewModel: TwitMainViewModel = get().koin.get()

    val adapter by lazy(LazyThreadSafetyMode.NONE) {
        TwitMainListAdapter(applicationContext!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.twit_main_controller, container, false).apply {
            twitMainRecycler.adapter = adapter
            twitMainRecycler.layoutManager = LinearLayoutManager(activity)
        }
    }


    override fun onAttach(view: View) {
        super.onAttach(view)
        viewModel.getToken()
        addToDisposables(
            com.jakewharton.rxbinding2.widget.RxTextView.afterTextChangeEvents(view.twitMainSearchEt)
                .skipInitialValue()
                .map(Function<com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent, String>() {
                    it.editable().toString().trim()
                })
                .debounce(
                    500.toLong(),
                    TimeUnit.MILLISECONDS,
                    io.reactivex.android.schedulers.AndroidSchedulers.mainThread()
                )
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe({
                    viewModel.search(it)
                }, {
                    Timber.d(it)
                })
        )
    }


    override fun onStateChange(state: Mvvm.State) {
        when (state) {
            is TwitMainViewModel.State.Authentication -> {
                Snackbar.make(view!!, "Authenticated", Snackbar.LENGTH_SHORT).show()
            }
            is TwitMainViewModel.State.Data -> {
                Timber.d(state.data.toString())
                adapter.addItems(state.data.tweets.map {
                    it.text
                })
            }
            is TwitMainViewModel.State.Error -> {
                Timber.e(state.error)
            }
        }
    }
}