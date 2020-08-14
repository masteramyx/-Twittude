package com.example.twittude.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.twittude.R
import com.example.twittude.ui.adapter.TwitMainListItemViewHolder
import com.example.twittude.ui.adapter.TwitRecyclerItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TwitListItem(val text: String) : TwitRecyclerItem {

    override fun getViewType(): Int {
        return R.layout.twit_main_recycler_list_item
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToRoot: Boolean
    ): TwitMainListItemViewHolder<*> {
        return TwitMainListItemViewHolder<TwitRecyclerItem>(
            inflater.inflate(
                getViewType(),
                parent,
                attachToRoot
            )
        )
    }

    override fun getRecyclerId(): String {
        return text
    }
}