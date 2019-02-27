package com.anenigmatic.secupia.screens.shared.data.retrofit

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {

    @POST("login")
    fun login(@Body body: RequestBody): Single<LoginResponse>

    @POST("reset-password")
    fun resetPassword(@Body body: RequestBody): Completable

    @GET("resident/profile")
    fun getProfile(@Header("Authorization") jwt: String): Single<GetProfileResponse>
}