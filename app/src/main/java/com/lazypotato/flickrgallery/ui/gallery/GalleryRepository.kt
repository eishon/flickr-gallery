package com.lazypotato.flickrgallery.ui.gallery

import com.lazypotato.flickrgallery.data.model.FlickrPhoto
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    fun fetchPhotosUsingTag(tag: String) : Flow<Result<List<FlickrPhoto>>>
}