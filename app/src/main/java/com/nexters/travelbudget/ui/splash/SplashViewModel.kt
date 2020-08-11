package com.nexters.travelbudget.ui.splash

import com.nexters.travelbudget.data.repository.UserTokenRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import io.reactivex.rxkotlin.addTo

/**
 * Splash 화면 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.30
 */

class SplashViewModel(
    private val userTokenRepository: UserTokenRepository
) : BaseViewModel() {

    private val _startMain = SingleLiveEvent<Unit>()
    val startMain = _startMain

    private val _startLogin = SingleLiveEvent<Unit>()
    val startLogin = _startLogin

    fun checkUserToken() {
        val userToken = userTokenRepository.getUserToken()
        if (userToken == null) {
            _startLogin.call()
        } else {
            if (userToken.expireDt.toLong() < System.currentTimeMillis()) { // access token 만료 됐을 경우
                refreshUserToken(userToken.refreshToken)
            } else {
                _startMain.call()
            }
        }
    }

    private fun refreshUserToken(refreshToken: String) {
        userTokenRepository.refreshUserToken(refreshToken)
            .applySchedulers()
            .subscribe({
                _startMain.call()
            }, {
                /** 토큰 갱신하는데 실패하는 경우 토큰 삭제 후 로그인 화면으로 전환 */
                userTokenRepository.deleteUserToken()
                _startLogin.call()
            }).addTo(compositeDisposable)
    }
}