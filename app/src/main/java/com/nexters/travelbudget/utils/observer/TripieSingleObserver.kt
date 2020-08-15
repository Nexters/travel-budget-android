package com.nexters.travelbudget.utils.observer

import android.widget.Toast
import com.nexters.travelbudget.R
import com.nexters.travelbudget.ui.base.TravelApplication
import com.nexters.travelbudget.utils.ext.isNetworkConnected
import com.nexters.travelbudget.utils.ext.showToastMessage
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException

/**
 * 서버 통신 시 발생하는 에러 핸들링하기 위한 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.31
 */

abstract class TripDisposableSingleObserver<T> :
    DisposableSingleObserver<T>() {

    override fun onError(e: Throwable) {
        with(TravelApplication.instance) {
            if ((e as? HttpException)?.code() == 401) {
                logout()
            } else {
                if (!isNetworkConnected()) {
                    showToastMessage(getString(R.string.network_disconnected_notice))
                }
            }
        }
    }
}