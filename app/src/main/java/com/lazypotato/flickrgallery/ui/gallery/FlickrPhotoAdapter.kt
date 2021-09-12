package com.lazypotato.flickrgallery.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.lazypotato.flickrgallery.R
import com.lazypotato.flickrgallery.data.model.FlickrPhoto
import com.lazypotato.flickrgallery.databinding.PhotoItemLayoutBinding

class FlickrPhotoAdapter constructor(
    private val glide: RequestManager,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<FlickrPhotoAdapter.FlickrPhotoViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<FlickrPhoto>() {
        override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
            return oldItem.link == newItem.link
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var photos: List<FlickrPhoto>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrPhotoViewHolder {
        val binding = PhotoItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return FlickrPhotoViewHolder(binding)
    }



    override fun onBindViewHolder(holder: FlickrPhotoViewHolder, position: Int) {
        val photo = photos[position]

        if(photo != null) {
            holder.bind(glide, photo, listener)
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class FlickrPhotoViewHolder(private val binding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(
                glide: RequestManager,
                photo: FlickrPhoto,
                listener: OnItemClickListener,
            ) {
                glide.load(photo.media.m).into(binding.imageView)

                var infoText = "${photo.title}"
                if(photo.author.isNotEmpty()){
                    infoText += "\nby ${photo.author}"
                }

                binding.title.text = infoText

                binding.root.setOnClickListener {
                    listener.onItemClick(photo)
                }
            }
        }

    interface OnItemClickListener {
        fun onItemClick(photo: FlickrPhoto)
    }
}