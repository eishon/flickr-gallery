package com.lazypotato.flickrgallery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlickrFeedResponse (
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<FlickrPhoto>,
) : Parcelable