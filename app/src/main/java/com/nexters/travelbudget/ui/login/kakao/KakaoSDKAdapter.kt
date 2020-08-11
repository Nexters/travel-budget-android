package com.nexters.travelbudget.ui.login.kakao

import android.content.Context
import com.kakao.auth.*

/**
 * 카카오 로그인 시 필요한 어댑터 (로그인 유형 선택 가능)
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.22
 */
class KakaoSDKAdapter(private val context: Context) : KakaoAdapter() {
    override fun getSessionConfig(): ISessionConfig {
        return object : ISessionConfig {
            override fun getAuthTypes(): Array<AuthType> = arrayOf(AuthType.KAKAO_TALK)

            override fun isUsingWebviewTimer(): Boolean = false

            override fun isSecureMode(): Boolean = false

            override fun getApprovalType(): ApprovalType = ApprovalType.INDIVIDUAL

            override fun isSaveFormData(): Boolean = true
        }
    }

    override fun getApplicationConfig(): IApplicationConfig =
        IApplicationConfig {
            context.applicationContext
        }
}