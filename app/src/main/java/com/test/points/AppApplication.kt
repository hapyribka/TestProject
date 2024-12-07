package com.test.points

import android.app.Application
import com.test.points.di.AppModules.initModules

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initModules(this@AppApplication)
    }
}
