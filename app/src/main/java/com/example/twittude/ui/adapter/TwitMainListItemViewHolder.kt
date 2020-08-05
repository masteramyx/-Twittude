package com.example.twittude.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.twit_main_recycler_list_item.view.*

class TwitMainListItemViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindView(tweet: String){
        itemView.twitMainRecyclerListItemTv.text = tweet
    }
}