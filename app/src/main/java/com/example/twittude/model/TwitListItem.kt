package com.example.twittude.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TwitListItem(val text: String) : Parcelable