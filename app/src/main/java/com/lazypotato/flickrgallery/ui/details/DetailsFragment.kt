package com.lazypotato.flickrgallery.ui.details

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.lazypotato.flickrgallery.R
import com.lazypotato.flickrgallery.data.model.FlickrPhoto
import com.lazypotato.flickrgallery.databinding.FragmentDetailsBinding
import com.lazypotato.flickrgallery.util.DateTimeUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var bitmap: Bitmap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        val photo = args.photo

        binding.apply {

            Glide.with(requireActivity())
                .asBitmap()
                .load(photo.media.m)
                .placeholder(R.drawable.placeholder_image)
                .error(android.R.drawable.stat_notify_error)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        bitmap = resource
                        binding.detailsImageView.setImageBitmap(resource)
                    }

                })

            binding.detailsTitle.text = "${photo.title}"
            binding.detailsAuthor.text = "${photo.author}"

            binding.detailsDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml("${photo.description}", Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("${photo.description}")
            }

            binding.detailsDateTaken.text =
                "${DateTimeUtil.parseDateTimeToAmPMString(photo.date_taken)}"
            binding.detailsPublished.text =
                "${DateTimeUtil.parseDateTimeToAmPMString(photo.published)}"

            binding.saveFab.setOnClickListener {
                saveToGallery()
            }

            binding.openBrowserFab.setOnClickListener {
                openInBrowser(photo)
            }

            binding.mailFab.setOnClickListener {
                sendMail()
            }
        }
    }

    private fun saveToGallery() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        );
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        );

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveImageForLatestVersions()
            } else {
                saveImageForPreviousVersions()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Image not saved", Toast.LENGTH_LONG).show()
        }
    }

    private fun openInBrowser(photo: FlickrPhoto) {
        val url = photo.link
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        startActivity(browserIntent)
    }

    private fun sendMail() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        );
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        );

        try {
            val uri: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveImageForLatestVersions()
            } else {
                saveImageForPreviousVersions()
            }

            uri?.let {
                val emailIntent = Intent(Intent.ACTION_SEND);
                emailIntent.type = "image/png";
                emailIntent.putExtra(Intent.EXTRA_STREAM, it)

                startActivity(emailIntent)
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Image not saved", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveImageForLatestVersions() : Uri? {
        val fos: FileOutputStream
        val resolver = requireContext().contentResolver
        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "${Calendar.getInstance().timeInMillis}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "FlickrPhotos"
            )
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        Objects.requireNonNull(uri)?.let {
            fos = resolver.openOutputStream(it) as FileOutputStream

            bitmap.compress(CompressFormat.JPEG, 100, fos)
            Objects.requireNonNull(fos)

            Toast.makeText(requireContext(), "Image saved", Toast.LENGTH_LONG).show()
        }

        return uri
    }

    private fun saveImageForPreviousVersions() : Uri? {
        var outputStream: FileOutputStream? = null
        val file = Environment.getExternalStorageDirectory()
        val dir = File(file.absolutePath + "/FlickrPhotos")
        dir.mkdirs()

        val filename = String.format("%d.png", System.currentTimeMillis())
        val outFile = File(dir, filename)
        try {
            outputStream = FileOutputStream(outFile)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        bitmap.compress(CompressFormat.PNG, 100, outputStream)
        try {
            outputStream!!.flush()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        try {
            outputStream!!.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return getUriForFile(
            requireContext(),
            "com.lazypotato.flickrgallery.provider",
            outFile
        )
    }

}