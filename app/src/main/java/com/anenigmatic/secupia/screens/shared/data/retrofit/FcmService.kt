package com.anenigmatic.secupia.screens.shared.data.retrofit

import io.reactivex.Completable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FcmService {

    @POST("registration-token")
    fun updateRegistrationToken(@Header("Authorization") jwt: String, @Body body: RequestBody): Completable
}