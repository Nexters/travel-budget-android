package com.nexters.travelbudget.ui.base

import androidx.lifecycle.ViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

/**
 * ViewModel Base 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.15
 */
abstract class BaseViewModel : ViewModel() {

    var liveToastMessage = SingleLiveEvent<String>()

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun showToast(message: String) {
        liveToastMessage.value = message
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}