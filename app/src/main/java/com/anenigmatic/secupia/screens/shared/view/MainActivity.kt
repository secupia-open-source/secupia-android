package com.anenigmatic.secupia.screens.shared.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.home.view.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.rootPOV, HomeFragment())
                .commit()

        }
    }
}
