package com.example.twittude.ui.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup

interface TwitRecyclerItem : Parcelable {

    fun getViewType(): Int

    fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToRoot: Boolean
    ): TwitMainListItemViewHolder<*>

    fun getRecyclerId(): String
}
