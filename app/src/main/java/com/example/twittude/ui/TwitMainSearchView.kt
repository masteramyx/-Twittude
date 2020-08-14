package com.example.twittude.ui

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.twittude.R

class TwitMainSearchView(context: Context) : LinearLayout(context) {

    init {
        View.inflate(context, R.layout.twit_main_search_view, this)
    }

}