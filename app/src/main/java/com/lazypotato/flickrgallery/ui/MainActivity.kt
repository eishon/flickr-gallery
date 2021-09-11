package com.lazypotato.flickrgallery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.lazypotato.flickrgallery.R
import com.lazypotato.flickrgallery.data.api.FlickrAPI
import com.lazypotato.flickrgallery.util.JSONPConverterUtil.GsonPConverterFactory
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl(FlickrAPI.BASE_URL)
            .addConverterFactory(GsonPConverterFactory(Gson()))
            .build()

        val flickrAPI = retrofit.create(FlickrAPI::class.java)
    }
}