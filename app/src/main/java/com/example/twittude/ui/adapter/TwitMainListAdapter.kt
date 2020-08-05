package com.example.twittude.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twittude.R
import kotlinx.android.synthetic.main.twit_main_controller.view.*

class TwitMainListAdapter(private val context: Context) : RecyclerView.Adapter<TwitMainListItemViewHolder>(){
    private var tweets: List<String> = emptyList()

    fun addItems(list: List<String>) {
        tweets = list
        if (tweets.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TwitMainListItemViewHolder {
        return TwitMainListItemViewHolder(LayoutInflater.from(context).inflate(R.layout.twit_main_recycler_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onBindViewHolder(holder: TwitMainListItemViewHolder, position: Int) {
        holder.bindView(tweets[position])
    }
}