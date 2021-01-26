package com.example.convert

import android.app.Application
import android.content.Context

class App: Application() {
    companion object {
        private var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    fun getAppContext(): Context? {
        return context
    }
}