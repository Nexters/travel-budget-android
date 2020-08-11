package com.nexters.travelbudget.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivitySplashBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.login.LoginActivity
import com.nexters.travelbudget.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 스플래쉬 액티비티
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.30
 */

class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    override val viewModel: SplashViewModel by viewModel()

    private val handler = Handler()

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkUserToken()

        viewModel.startLogin.observe(this, Observer {
            goToLoginActivity()
        })
        viewModel.startMain.observe(this, Observer {
            goToMainActivity()
        })
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun goToMainActivity() {
        setIntentHandler(MainActivity.getIntent(this))
    }

    private fun goToLoginActivity() {
        setIntentHandler(LoginActivity.getIntent(this))
    }

    private fun setIntentHandler(intent: Intent) {
        handler.postDelayed({
            startActivity(intent)
            finish()
        }, 1500)
    }
}