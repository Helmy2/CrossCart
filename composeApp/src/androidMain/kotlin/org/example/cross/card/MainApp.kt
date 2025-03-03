package org.example.cross.card

import android.app.Application
import org.example.cross.card.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApp)
        }
    }
}