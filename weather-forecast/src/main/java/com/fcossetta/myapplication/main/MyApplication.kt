package com.fcossetta.myapplication.main

import android.app.Application
import com.fcossetta.myapplication.main.data.di.glideModule
import com.fcossetta.myapplication.main.data.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication.applicationContext)
            modules(listOf(networkModule, glideModule))
        }
    }
}