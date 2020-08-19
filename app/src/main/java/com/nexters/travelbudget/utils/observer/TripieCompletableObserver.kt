package com.nexters.travelbudget.utils.observer

import com.nexters.travelbudget.R
import com.nexters.travelbudget.ui.base.TravelApplication
import com.nexters.travelbudget.utils.ext.isNetworkConnected
import com.nexters.travelbudget.utils.ext.showToastMessage
import io.reactivex.observers.DisposableCompletableObserver
import org.json.JSONObject
import retrofit2.HttpException

/**
 * Completable 을 위한 통신 에러 핸들링 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.14
 */
abstract class TripieCompletableObserver : DisposableCompletableObserver() {

    override fun onError(e: Throwable) {
        with(TravelApplication.instance) {
            if ((e as? HttpException)?.code() == 401) {
                logout()
            } else if ((e as? HttpException)?.code() == 400) {
                val errorResult = e.response()?.errorBody()?.string() ?: return
                val jsonObjError = JSONObject(errorResult)
                val message = jsonObjError.getString("message")
                showToastMessage(message)
            } else {
                if (!isNetworkConnected()) {
                    showToastMessage(getString(R.string.network_disconnected_notice))
                }
            }
        }
    }
}