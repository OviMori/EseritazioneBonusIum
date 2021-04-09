package com.example.eseritazionebonusium

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataRepository.init(this)
    }
}