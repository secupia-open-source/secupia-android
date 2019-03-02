package com.anenigmatic.secupia.screens.shared.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.anenigmatic.secupia.screens.shared.core.User
import com.anenigmatic.secupia.screens.shared.data.retrofit.FcmService
import com.anenigmatic.secupia.screens.shared.data.retrofit.UserService
import com.anenigmatic.secupia.screens.shared.util.toRequestBody
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class UserRepositoryImpl(
    private val prefs: SharedPreferences,
    private val uService: UserService,
    private val fService: FcmService
) : UserRepository {

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
        val body = JSONObject().apply {
            put("username", username)
            put("password", password)
        }
        return uService.login(body.toRequestBody())
            .doOnSuccess { loginResponse ->
                prefs.edit(commit = true) {
                    putString("JWT", "JWT ${loginResponse.jwt}")
                }
            }
            .flatMapCompletable { loginResponse ->
                uService.getProfile("JWT ${loginResponse.jwt}")
                    .doOnSuccess { getProfileResponse ->
                        prefs.edit(commit = true) {
                            putString("NAME", getProfileResponse.profile.name)
                            putString("FLAT", getProfileResponse.profile.flat)
                            putStringSet("VEHICLE_NUMBERS", getProfileResponse.vehicles.map { it.registrationNo }.toSet())
                        }
                    }
                    .doOnSuccess { getProfileResponse ->
                        val body = JSONObject().apply {
                            put("registration_token", prefs.getString("REGISTRATION_TOKEN", null))
                        }
                        fService.updateRegistrationToken("JWT ${loginResponse.jwt}", body.toRequestBody())
                            .subscribe(
                                {},{}
                            )
                    }
                    .ignoreElement()
            }
    }

    override fun resetPassword(username: String): Completable {
        val body = JSONObject().apply {
            put("username", username)
        }
        return uService.resetPassword(body.toRequestBody())
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
    }
}