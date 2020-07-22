package com.nexters.travelbudget.ui.login.kakao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.UserAccount
import com.kakao.util.exception.KakaoException

/**
 * 카카오 로그인 관리 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.22
 */

typealias OnLoginSuccess<T> = (T) -> Unit

typealias OnLoginFail = (Throwable) -> Unit

class KakaoLogin(
    private val activity: AppCompatActivity,
    private val onLoginSuccess: OnLoginSuccess<UserAccount>? = null,
    private val onLoginFail: OnLoginFail? = null
) {

    private var sessionCallback: SessionCallback = SessionCallback()

    private val session by lazy {
        Session.getCurrentSession()
    }

    /** LoginActivity onActivityResult 에서 받은 값을 카카오 Session 클래스로 값을 넘겨주기 위한 메소드 */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)
    }

    /** 카카오 로그인을 처리하기 위한 메소드  */
    fun login() {
        session.addCallback(sessionCallback)
        session.open(AuthType.KAKAO_TALK, activity)
    }

    /** 카카오 로그아웃을 처리하기 위한 메소드 */
    fun logout() {
        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {

            }
        })
    }

    /**
     * sessionCallback 이 중첩으로 들어가는 걸 막기 위한 메소드
     * 중첩으로 들어가게 되면 SessionCallback 이 여러번 호출되는 이슈가 있음
     *  */
    fun onDestroy() {
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    /** 로그인 세션을 성공, 실패 여부를 받기 위한 클래스 */
    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null && !exception.isCancledOperation) {
                onLoginFail?.invoke(exception) // 실패 시 실패 값을 액티비티로 전달
            }
        }

        override fun onSessionOpened() {
            requestProfile() // 성공 시 사용자 프로필 정보 요청 메소드 호출
        }
    }

    /** 사용자 프로필을 요청하기 위한 메소드 */
    private fun requestProfile() {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {

            override fun onSessionClosed(errorResult: ErrorResult) {

            }

            override fun onSuccess(result: MeV2Response) {
                onLoginSuccess?.invoke(result.kakaoAccount)
            }

            override fun onFailure(errorResult: ErrorResult) {
                super.onFailure(errorResult)
                onLoginFail?.invoke(errorResult.exception)
            }
        })
    }
}