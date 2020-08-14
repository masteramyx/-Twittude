package com.example.twittude.ui.adapter

import android.view.View
import com.example.twittude.model.TwitListSearchItem
import com.karakum.base.recycler.BaseRecyclerViewHolder
import kotlinx.android.synthetic.main.twit_main_search_view.view.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TwitMainSearchListItemViewHolder(view: View) :
    BaseRecyclerViewHolder<TwitListSearchItem>(view) {

    var listener: SearchListener? = null

    interface SearchListener {
        fun onSearched(query: String)
    }

    override fun bindItem(item: TwitListSearchItem) {
        addToDisposables(
            com.jakewharton.rxbinding2.widget.RxTextView.afterTextChangeEvents(itemView.twitMainSearchEt)
                .skipInitialValue()
                .map {
                    it.editable().toString().trim()
                }
                .debounce(
                    500.toLong(),
                    TimeUnit.MILLISECONDS,
                    io.reactivex.android.schedulers.AndroidSchedulers.mainThread()
                )
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe({
                    listener?.onSearched(it)
                }, {
                    Timber.d(it)
                })
        )
    }
}