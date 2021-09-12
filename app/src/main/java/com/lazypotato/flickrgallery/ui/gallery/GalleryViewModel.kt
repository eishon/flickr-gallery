package com.lazypotato.flickrgallery.ui.gallery

import androidx.lifecycle.*
import com.lazypotato.flickrgallery.data.model.FlickrPhoto

class GalleryViewModel constructor(private val repository: GalleryRepository) : ViewModel() {
    private val currentTag = MutableLiveData("")

    val flickrPhotos = currentTag.switchMap { tag ->
        repository.fetchPhotosUsingTag(tag).asLiveData()
    }

    fun searchPhotosByTag(tag: String) {
        currentTag.postValue(tag)
    }
}