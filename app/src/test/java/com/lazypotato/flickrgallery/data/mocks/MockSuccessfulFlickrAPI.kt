package com.lazypotato.flickrgallery.data.mocks

import com.lazypotato.flickrgallery.data.api.FlickrAPI
import com.lazypotato.flickrgallery.data.model.FlickrFeedResponse

object MockSuccessfulFlickrAPI : FlickrAPI {
    override suspend fun fetchPhotosUsingTags(tags: String, format: String): FlickrFeedResponse? {
        return MockDataSource.flickrFeedResponse
    }
}