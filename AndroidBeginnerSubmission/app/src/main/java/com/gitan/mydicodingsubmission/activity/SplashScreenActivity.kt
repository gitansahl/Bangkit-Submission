package com.gitan.mydicodingsubmission.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.gitan.mydicodingsubmission.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashScreenBinding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val mainActivityIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }, 2000)
    }
}