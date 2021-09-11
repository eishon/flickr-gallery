package com.lazypotato.flickrgallery.data.model

data class FlickrFeedResponse (
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<FlickrPhoto>,
)