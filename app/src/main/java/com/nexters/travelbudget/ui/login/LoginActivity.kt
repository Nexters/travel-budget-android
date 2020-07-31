package com.nexters.travelbudget.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityLoginBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.login.kakao.KakaoLogin
import com.nexters.travelbudget.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 로그인 액티비티
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.24
 */

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    private val kakaoLogin by lazy {
        KakaoLogin(this, onLoginSuccess = { signupRequest ->
            viewModel.requestSignUp(signupRequest)
        }, onLoginFail = {
            reStartActivity()
        })
    }

    override val viewModel: LoginViewModel by viewModel {
        parametersOf(kakaoLogin)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.startMain.observe(this, Observer {
            startActivity(MainActivity.getIntent(this))
        })

        viewModel.reStartLogin.observe(this, Observer {
            reStartActivity()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        kakaoLogin.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        kakaoLogin.onDestroy()
        super.onDestroy()
    }

    private fun reStartActivity() {
        viewModel.showToast(getString(R.string.connection_failed))
        val intent = intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        finish()
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
