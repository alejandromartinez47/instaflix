package co.instaflix.splash.view

import android.content.Intent
import co.instaflix.R
import co.instaflix.config.view.BaseActivity
import android.os.Handler;
import android.os.Looper
import co.instaflix.navigation.view.NavigationActivity


class SplashActivity: BaseActivity() {


    override fun findLayoutById() = R.layout.activity_splash
    override fun initUi() {
        splashscreenstart()
    }

    fun splashscreenstart() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, NavigationActivity::class.java))
            finish()
        }, 3000)
    }

}