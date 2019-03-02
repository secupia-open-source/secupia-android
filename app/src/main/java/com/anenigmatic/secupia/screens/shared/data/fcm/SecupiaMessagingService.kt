package com.anenigmatic.secupia.screens.shared.data.fcm

import android.content.SharedPreferences
import androidx.core.content.edit
import com.anenigmatic.secupia.SecupiaApp
import com.google.firebase.messaging.FirebaseMessagingService
import javax.inject.Inject

class SecupiaMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var prefs: SharedPreferences

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)

        SecupiaApp.appComponent.inject(this)

        prefs.edit(commit = true) {
            putString("REGISTRATION_TOKEN", p0)
        }
    }
}