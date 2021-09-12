package com.lazypotato.flickrgallery.ui.details

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lazypotato.flickrgallery.R
import com.lazypotato.flickrgallery.databinding.FragmentDetailsBinding
import com.lazypotato.flickrgallery.util.DateTimeUtil
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        val glide = Glide.with(requireActivity()).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
        )

        val photo = args.photo

        binding.apply {

            glide.load(photo.media.m).into(binding.detailsImageView)

            binding.detailsTitle.text = "${photo.title}"
            binding.detailsAuthor.text = "${photo.author}"

            binding.detailsDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml("${photo.description}", Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("${photo.description}")
            }

            binding.detailsDateTaken.text = "${DateTimeUtil.parseDateTimeToAmPMString(photo.date_taken)}"
            binding.detailsPublished.text = "${DateTimeUtil.parseDateTimeToAmPMString(photo.published)}"
        }
    }
}