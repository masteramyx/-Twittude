package com.example.twittude.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twittude.model.TwitListItem

class TwitMainListAdapter<T : TwitRecyclerItem>(private val context: Context) :
    RecyclerView.Adapter<TwitMainListItemViewHolder<T>>() {
    private var tweets = mutableListOf<TwitRecyclerItem>()

    fun addItems(list: List<TwitRecyclerItem>) {
        tweets.addAll(list)
        if (tweets.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }

    fun addItem(item: TwitRecyclerItem) {
        tweets.clear()
        tweets.add(item)
    }

    override fun getItemViewType(position: Int): Int {
        return tweets[position].getViewType()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TwitMainListItemViewHolder<T> {
        return tweets.first { item ->
            item.getViewType() == viewType
        }.createViewHolder(
            LayoutInflater.from(context),
            parent,
            false
        ) as TwitMainListItemViewHolder<T>
    }

    override fun getItemCount(): Int {
        return tweets.size
    }


    override fun onBindViewHolder(holder: TwitMainListItemViewHolder<T>, position: Int) {
        if (tweets[position] is TwitListItem)
            holder.bindView((tweets[position] as TwitListItem).text)
    }

}