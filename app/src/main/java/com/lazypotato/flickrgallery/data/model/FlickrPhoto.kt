package com.lazypotato.flickrgallery.data.model

data class FlickrPhoto(
    val title: String,
    val link: String,
    val media: Media,
    val date_taken: String,
    val description: String,
    val published: String,
    val author: String,
    val author_id: String,
    val tags: String,
)

data class Media (val m: String)
