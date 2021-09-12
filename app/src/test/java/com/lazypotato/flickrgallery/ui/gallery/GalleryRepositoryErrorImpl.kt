package com.lazypotato.flickrgallery.ui.gallery

import com.lazypotato.flickrgallery.data.api.FlickrAPI
import com.lazypotato.flickrgallery.data.mocks.MockDataSource
import com.lazypotato.flickrgallery.data.mocks.MockFailedFlickrAPI
import com.lazypotato.flickrgallery.data.mocks.MockSuccessfulFlickrAPI
import com.lazypotato.flickrgallery.data.model.FlickrPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException

class GalleryRepositoryErrorImpl constructor(private val api: FlickrAPI) : GalleryRepository{
    override fun fetchPhotosUsingTag(tag: String): Flow<Result<List<FlickrPhoto>>> {
        return flow {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}