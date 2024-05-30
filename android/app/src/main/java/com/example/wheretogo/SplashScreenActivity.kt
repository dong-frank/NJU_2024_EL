package com.example.wheretogo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi

/**
 * 开屏界面
 */
class SplashScreenActivity : BaseActivity() {
        private val SPLASH_TIME_OUT:Long = 1000 // 1 sec

        @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash_screen)

            Handler().postDelayed({
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(Intent(this,MainActivity::class.java))
                // close this activity
                finish()
            }, SPLASH_TIME_OUT)
        }
}
