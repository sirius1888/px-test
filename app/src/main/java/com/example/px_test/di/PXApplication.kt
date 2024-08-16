package com.example.px_test.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.vk.id.VKID
@HiltAndroidApp
class PXApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        VKID.logsEnabled = true
        VKID.init(this)
    }
}
