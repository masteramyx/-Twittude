package com.example.twittude.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.twittude.R
import com.example.twittude.ui.adapter.TwitMainSearchListItemViewHolder
import com.karakum.base.recycler.BaseRecyclerItem
import com.karakum.base.recycler.BaseRecyclerViewHolder
import kotlinx.android.parcel.Parcelize

@Parcelize
class TwitListSearchItem : BaseRecyclerItem {
    override fun getViewType(): Int {
        return R.layout.twit_main_search_view
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToRoot: Boolean
    ): BaseRecyclerViewHolder<*> {
        return TwitMainSearchListItemViewHolder(
            inflater.inflate(
                getViewType(),
                parent,
                attachToRoot
            )
        )
    }

    override fun getRecyclerId(): String {
        return "search"
    }
}