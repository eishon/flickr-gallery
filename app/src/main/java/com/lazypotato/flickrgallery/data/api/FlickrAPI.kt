package com.lazypotato.flickrgallery.data.api

import com.lazypotato.flickrgallery.data.model.FlickrFeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrAPI {

    companion object {
        const val BASE_URL = "https://www.flickr.com/"
    }

    @GET("services/feeds/photos_public.gne")
    suspend fun fetchPhotosUsingTags(
        @Query("tags") tags: String,
        @Query("format") format: String = "json",
    ): FlickrFeedResponse?

}