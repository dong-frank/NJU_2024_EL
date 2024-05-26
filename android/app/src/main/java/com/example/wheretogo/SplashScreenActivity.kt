package com.example.wheretogo

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wheretogo.ui.theme.WhereToGoTheme

class SplashScreenActivity : BaseActivity() {
        private val SPLASH_TIME_OUT:Long = 1500 // 3 sec

        @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = Color.TRANSPARENT
            }
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
