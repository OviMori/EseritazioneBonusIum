package com.example.eseritazionebonusium

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataRepository.init(this)
    }
}