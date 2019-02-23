package com.anenigmatic.secupia.screens.shared.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.anenigmatic.secupia.screens.shared.core.User
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class UserRepositoryImpl(private val prefs: SharedPreferences) : UserRepository {

    override fun getUser(forceUpdate: Boolean): Flowable<User> {
        val userFlowable = Flowable.create<User>({ emitter ->
            val jwt = prefs.getString("JWT", null)
            val name = prefs.getString("NAME", null)
            val flat = prefs.getString("FLAT", null)
            val vehicleNumbers = prefs.getStringSet("VEHICLE_NUMBERS", null)

            if(jwt == null || name == null || flat == null || vehicleNumbers == null) {
                emitter.onError(IllegalStateException("User isn't logged in"))
                return@create
            }

            emitter.onNext(User(jwt, name, flat, vehicleNumbers.toList()))
        }, BackpressureStrategy.LATEST)

        return if(forceUpdate) {
            userFlowable.delay(1, TimeUnit.SECONDS)
        } else {
            userFlowable
        }
    }

    override fun login(username: String, password: String): Completable {
        return Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .doOnComplete {
                prefs.edit(commit = true) {
                    putString("JWT", "JWT ealkja;kj")
                    putString("NAME", "Nishant Mahajan")
                    putString("FLAT", "Block-A, RM2109")
                    putStringSet("VEHICLE_NUMBERS", setOf("RJ14 9862", "RJ14 8915"))
                }
            }
    }

    override fun resetPassword(username: String): Completable {
        return Completable.complete()
            .delay(2, TimeUnit.SECONDS)
    }

    override fun logout(): Completable {
        return Completable.create { emitter ->
            prefs.edit(commit = true) {
                putString("JWT", null)
                putString("NAME", null)
                putString("FLAT", null)
                putStringSet("VEHICLE_NUMBERS", null)
            }

            emitter.onComplete()
        }
            .delay(1, TimeUnit.SECONDS)
    }
}