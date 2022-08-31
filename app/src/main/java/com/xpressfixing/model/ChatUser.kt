package com.pixelite.foodiecom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ChatUser(
    val firstName: String,
    val userName: String
) : Parcelable
