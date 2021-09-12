package com.lazypotato.flickrgallery.ui.gallery

import com.lazypotato.flickrgallery.data.api.FlickrAPI
import com.lazypotato.flickrgallery.data.model.FlickrPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

class GalleryRepositoryImpl constructor(private val api: FlickrAPI) : GalleryRepository {

    override fun fetchPhotosUsingTag(
            tag: String
    ): Flow<Result<List<FlickrPhoto>>> {
        return flow {
            val response = api.fetchPhotosUsingTags(tag)

            if(response == null) {
                emit(Result.failure(RuntimeException("Something went wrong")))
            } else {
                emit(Result.success(response.items))
            }
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}