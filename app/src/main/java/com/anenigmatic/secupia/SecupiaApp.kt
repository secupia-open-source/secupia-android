package com.anenigmatic.secupia

import android.app.Application
import com.anenigmatic.secupia.di.shared.AppComponent
import com.anenigmatic.secupia.di.shared.AppModule
import com.anenigmatic.secupia.di.shared.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen

class SecupiaApp : Application() {

    companion object {

        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        AndroidThreeTen.init(this)
    }
}