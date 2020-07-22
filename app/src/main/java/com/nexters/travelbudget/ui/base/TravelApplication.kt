package com.nexters.travelbudget.ui.base

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.nexters.travelbudget.BuildConfig
import com.nexters.travelbudget.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TravelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        debugMode = isDebuggable(this)

        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@TravelApplication)
            modules(networkModule)
        }
    }

    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false

        val pm = context.packageManager
        try {
            val appInfo = pm.getApplicationInfo(context.packageName, 0)
            debuggable = (0 != ((appInfo.flags) and ApplicationInfo.FLAG_DEBUGGABLE))
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return debuggable
    }

    companion object {
        lateinit var instance: TravelApplication
            private set
        var debugMode: Boolean = true
    }
}