package com.texttranslate.voice

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        instance = this

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: MyApp? = null
    }
}