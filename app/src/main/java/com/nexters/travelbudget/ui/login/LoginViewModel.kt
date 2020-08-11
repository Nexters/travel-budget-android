package com.nexters.travelbudget.ui.login

import com.nexters.travelbudget.data.remote.model.request.SignUpRequest
import com.nexters.travelbudget.data.repository.SignUpRepository
import com.nexters.travelbudget.data.repository.UserTokenRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.ui.login.kakao.KakaoLogin
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
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

class LoginViewModel(
    private val kakaoLogin: KakaoLogin,
    private val signUpRepository: SignUpRepository,
    private val userTokenRepository: UserTokenRepository
) : BaseViewModel() {

    private val kakaoLoginClick: PublishSubject<Unit> = PublishSubject.create()

    private val _startMain = SingleLiveEvent<Unit>()
    val startMain = _startMain

    private val _reStartLogin = SingleLiveEvent<Unit>()
    val reStartLogin = _reStartLogin

    init {
        kakaoLoginClick
            .throttleFirst(4000L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                kakaoLogin.login()
            }.addTo(compositeDisposable)
    }

    private fun createUserToken(kakaoId: String) {
        userTokenRepository.createUserToken(kakaoId)
            .applySchedulers()
            .subscribe({
                _startMain.call()
            }, {
                _reStartLogin.call()
            }).addTo(compositeDisposable)
    }

    fun requestSignUp(signUpRequest: SignUpRequest) {
        signUpRepository.requestSignUp(signUpRequest)
            .applySchedulers()
            .subscribe({ signUpResponse ->
                createUserToken(signUpResponse.kakaoId)
            }, {
                _reStartLogin.call()
            }).addTo(compositeDisposable)
    }

    fun onKakaoClick() {
        kakaoLoginClick.onNext(Unit)
    }
}