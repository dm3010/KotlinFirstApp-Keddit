package com.dm3010.keddit

import android.app.Application

class KedditApp : Application() {

    override fun onCreate() {
        super.onCreate()
        println("Hi")
    }
}