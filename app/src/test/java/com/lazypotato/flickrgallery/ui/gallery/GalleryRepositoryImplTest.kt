package com.lazypotato.flickrgallery.ui.gallery

import com.lazypotato.flickrgallery.data.mocks.MockDataSource
import com.lazypotato.flickrgallery.data.mocks.MockFailedFlickrAPI
import com.lazypotato.flickrgallery.data.mocks.MockSuccessfulFlickrAPI
import com.lazypotato.flickrgallery.data.model.FlickrPhoto
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Test
import java.lang.RuntimeException

class GalleryRepositoryImplTest {

    private lateinit var repository: GalleryRepository

    private val photos = MockDataSource.flickrFeedResponse.items

    private val tags = ""

    @Test
    fun `Fetch photos should return Flow of List of FlickrPhoto`() = runBlockingTest {
        mockSuccessfulCase()

        repository.fetchPhotosUsingTag(tags).collect {
            assert(it.getOrNull() is List<FlickrPhoto>)
        }
    }

    @Test
    fun `Fetch photos should be correct`() = runBlockingTest {
        mockSuccessfulCase()

        repository.fetchPhotosUsingTag(tags).collect {
            assertEquals(it.getOrDefault(listOf()), photos)
        }
    }

    @Test
    fun `Fetch photos should return error if returns error`() = runBlockingTest {
        mockErrorCase()

        repository.fetchPhotosUsingTag(tags).collect {
            assertEquals(it.exceptionOrNull()?.message, "Something went wrong")
        }
    }

    @Test
    fun `Fetch photos should return error if doesn't return correct Flow of List of FlickrPhoto`() = runBlockingTest {
        mockFailureCase()

        repository.fetchPhotosUsingTag(tags).collect {
            assertNotEquals(it.getOrNull(), photos)
        }
    }


    // As we can't use Mockito 3rd Party Library
    private fun mockSuccessfulCase() {
        repository = GalleryRepositoryImpl(MockSuccessfulFlickrAPI)
    }

    private fun mockFailureCase() {
        repository = GalleryRepositoryImpl(MockFailedFlickrAPI)
    }

    private fun mockErrorCase() {
        repository = GalleryRepositoryErrorImpl(MockSuccessfulFlickrAPI)
    }
}