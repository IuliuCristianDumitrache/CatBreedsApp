package com.example.cat

import android.app.Application
import com.example.cat.data.DatabaseHelper.initDB
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDB(applicationContext)
    }
}