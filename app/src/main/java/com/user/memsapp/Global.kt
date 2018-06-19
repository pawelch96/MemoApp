package com.user.memsapp

import android.app.Application
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient

public class Global : Application() {

    override fun onCreate() {
        super.onCreate()

        val client = OkHttpClient()
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this))
        val built : Picasso = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }
}
