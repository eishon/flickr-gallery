package com.lazypotato.flickrgallery.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.lazypotato.flickrgallery.R
import com.lazypotato.flickrgallery.data.api.FlickrAPI
import com.lazypotato.flickrgallery.data.model.FlickrPhoto
import com.lazypotato.flickrgallery.databinding.FragmentGalleryBinding
import com.lazypotato.flickrgallery.util.JSONPConverterUtil.GsonPConverterFactory
import retrofit2.Retrofit

/*
- Didn't use Hilt as the project is small
- Didn't implemented other than Unit Test as it was not mentioned in the Tasks
- Memory Caching is default on Glide, didn't implemented disk cache
 */

class GalleryFragment : Fragment(R.layout.fragment_gallery),
    FlickrPhotoAdapter.OnItemClickListener {

    private lateinit var flickrAPI: FlickrAPI

    private lateinit var viewModel: GalleryViewModel

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        setupAPIService()
        setUpViewModel()

        val glide = Glide.with(requireActivity()).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
        )


        val adapter = FlickrPhotoAdapter(glide, this)

        binding.apply {
            galleryRecyclerView.setHasFixedSize(true)
            galleryRecyclerView.itemAnimator = null
            galleryRecyclerView.adapter = adapter

            retryButton.setOnClickListener {
                viewModel.searchPhotosByTag("")
            }
        }

        viewModel.flickrPhotos.observe(viewLifecycleOwner) { data ->
            viewModel.loading.postValue(false)
            if (data.isSuccess) {
                binding.noDataLayout.visibility = View.GONE
                binding.galleryRecyclerView.visibility = View.VISIBLE

                adapter.photos = data.getOrDefault(listOf())
            } else {
                binding.noDataLayout.visibility = View.VISIBLE
                binding.galleryRecyclerView.visibility = View.GONE
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        setHasOptionsMenu(true)
    }

    private fun setupAPIService() {
        val retrofit = Retrofit.Builder()
            .baseUrl(FlickrAPI.BASE_URL)
            .addConverterFactory(GsonPConverterFactory(Gson()))
            .build()

        flickrAPI = retrofit.create(FlickrAPI::class.java)
    }

    private fun setUpViewModel() {
        val repository = GalleryRepositoryImpl(flickrAPI)

        val viewModelFactory = GalleryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GalleryViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        val clearButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)

        clearButton.setOnClickListener {
            if(searchView.query.isEmpty()) {
                searchView.isIconified = true;
            } else {
                viewModel.searchPhotosByTag("")

                searchView.setQuery("", false);
            }

        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var searchTag = ""

                query?.let {
                    searchTag = it
                }

                viewModel.searchPhotosByTag(searchTag)

                searchView.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onItemClick(photo: FlickrPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }
}