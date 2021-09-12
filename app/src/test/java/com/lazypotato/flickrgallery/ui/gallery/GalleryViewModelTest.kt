package com.lazypotato.flickrgallery.ui.gallery

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lazypotato.flickrgallery.data.mocks.MockDataSource
import com.lazypotato.flickrgallery.data.mocks.MockFailedFlickrAPI
import com.lazypotato.flickrgallery.data.mocks.MockSuccessfulFlickrAPI
import com.lazypotato.flickrgallery.util.MainCoroutineScopeRule
import com.lazypotato.flickrgallery.util.getValueForTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class GalleryViewModelTest {
    private lateinit var viewModel: GalleryViewModel

    private val photos = MockDataSource.flickrFeedResponse.items

    private val tags = ""

    @get:Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `Fetch photos should be correct`() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(viewModel.flickrPhotos.getValueForTest()?.getOrDefault(listOf()), photos)
    }

    @Test
    fun `Fetch photos should return error if returns error`() = runBlockingTest {
        mockErrorCase()

        assertEquals(
            viewModel.flickrPhotos.getValueForTest()?.exceptionOrNull()?.message,
            "Something went wrong"
        )
    }

    @Test
    fun `Fetch photos should return error if doesn't return correct Flow of List of FlickrPhoto`() =
        runBlockingTest {
            mockFailureCase()

            assertNotEquals(viewModel.flickrPhotos.getValueForTest()?.getOrNull(), photos)
        }


    // As we can't use Mockito 3rd Party Library
    private fun mockSuccessfulCase() {
        val repository = GalleryRepositoryImpl(MockSuccessfulFlickrAPI)

        viewModel = GalleryViewModel(repository)
    }

    private fun mockFailureCase() {
        val repository = GalleryRepositoryImpl(MockFailedFlickrAPI)

        viewModel = GalleryViewModel(repository)
    }

    private fun mockErrorCase() {
        val repository = GalleryRepositoryErrorImpl(MockSuccessfulFlickrAPI)

        viewModel = GalleryViewModel(repository)
    }
}