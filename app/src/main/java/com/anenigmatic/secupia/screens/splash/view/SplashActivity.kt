package com.anenigmatic.secupia.screens.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.shared.view.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_splash)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}