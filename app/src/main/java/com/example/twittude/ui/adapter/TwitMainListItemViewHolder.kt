package com.example.twittude.ui.adapter

import android.view.View
import com.example.twittude.model.TwitListItem
import com.karakum.base.recycler.BaseRecyclerItem
import com.karakum.base.recycler.BaseRecyclerViewHolder
import kotlinx.android.synthetic.main.twit_main_recycler_list_item.view.*

class TwitMainListItemViewHolder(view: View) :
    BaseRecyclerViewHolder<BaseRecyclerItem>(view) {

    var listener: ClickListener? = null

    interface ClickListener {
        fun onItemClicked(tweet: String)
    }

    override fun bindItem(item: BaseRecyclerItem) {
        itemView.setOnClickListener {
            listener?.onItemClicked((item as TwitListItem).text)
        }
        when (item) {
            is TwitListItem -> {
                itemView.twitMainRecyclerListItemTv.text = item.text
            }
        }
    }
}