package com.nexters.travelbudget.ui.base

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.kakao.auth.KakaoSDK
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.nexters.travelbudget.BuildConfig
import com.nexters.travelbudget.data.local.prefs.UserTokenManager
import com.nexters.travelbudget.di.*
import com.nexters.travelbudget.ui.login.LoginActivity
import com.nexters.travelbudget.ui.login.kakao.KakaoSDKAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TravelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        debugMode = isDebuggable(this)

        KakaoSDK.init(KakaoSDKAdapter(this))

        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@TravelApplication)
            modules(networkModule, viewModelModule, repositoryModule, remoteModule, localModule)
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

    fun logout() {
        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                UserTokenManager.deleteUserToken(this@TravelApplication)
                val intent = LoginActivity.getIntent(this@TravelApplication).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }
        })
    }

    companion object {
        lateinit var instance: TravelApplication
            private set
        var debugMode: Boolean = true
    }
}