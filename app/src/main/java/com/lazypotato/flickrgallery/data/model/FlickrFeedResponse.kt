package com.lazypotato.flickrgallery.data.model

import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlickrFeedResponse (
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<FlickrPhoto>,
)