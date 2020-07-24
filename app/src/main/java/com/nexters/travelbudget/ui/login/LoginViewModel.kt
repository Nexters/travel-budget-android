package com.nexters.travelbudget.ui.login

import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.ui.login.kakao.KakaoLogin
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * 로그인 관련 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.24
 */

class LoginViewModel(private val kakaoLogin: KakaoLogin) : BaseViewModel() {

    private val kakaoLoginClick: PublishSubject<Unit> = PublishSubject.create()

    init {
        kakaoLoginClick
            .throttleFirst(4000L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                kakaoLogin.login()
            }.addTo(compositeDisposable)
    }

    fun onKakaoClick() {
        kakaoLoginClick.onNext(Unit)
    }
}