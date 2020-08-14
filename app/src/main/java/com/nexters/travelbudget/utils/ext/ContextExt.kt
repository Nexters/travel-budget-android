package com.nexters.travelbudget.utils.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import com.nexters.travelbudget.R
import com.nexters.travelbudget.utils.Constant
import kotlinx.android.synthetic.main.view_toast_message.view.*

/**
 * Context 관련 Extension methods
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.18
 */

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

@SuppressLint("InflateParams")
fun Context.showToastMessage(message: String) {
    val view: View = LayoutInflater.from(this).inflate(R.layout.view_toast_message, null, false)
    view.tv_toast_message.text = message

    Toast(this).apply {
        this.view = view
        this.duration = Toast.LENGTH_SHORT
    }.show()
}

fun Context.putSharedPreference(key: String, data: Any) {
    getSharedPreferences(Constant.PREF_NAME, Activity.MODE_PRIVATE).run {
        edit(true) {
            when (data) {
                is String -> putString(key, data)
                is Boolean -> putBoolean(key, data)
                is Int -> putInt(key, data)
                is Float -> putFloat(key, data)
                is Long -> putLong(key, data)
            }
        }
    }
}

@Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
fun <T> Context.getSharedPreference(key: String, defaultData: T): T {
    val sharedPreferences = getSharedPreferences(Constant.PREF_NAME, Activity.MODE_PRIVATE)
    return with(sharedPreferences) {
        when (defaultData) {
            is String -> getString(key, defaultData)
            is Boolean -> getBoolean(key, defaultData)
            is Int -> getInt(key, defaultData)
            is Float -> getFloat(key, defaultData)
            is Long -> getLong(key, defaultData)
            else -> null
        } as T
    }
}