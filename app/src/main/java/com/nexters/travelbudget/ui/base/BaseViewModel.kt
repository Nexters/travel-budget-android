package com.nexters.travelbudget.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * ViewModel Base 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.15
 */
abstract class BaseViewModel : ViewModel() {

    var liveToastMessage: MutableLiveData<String> = MutableLiveData()

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun showToast(message: String) {
        liveToastMessage.value = message
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}