package com.lazypotato.flickrgallery.data.model

import android.os.Parcelable
import com.lazypotato.flickrgallery.util.DateTimeUtil
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var dateTakenMillis: Long,
    var publishedMillis: Long
) : Parcelable {

    @Parcelize
    data class Media (val m: String) : Parcelable

}



