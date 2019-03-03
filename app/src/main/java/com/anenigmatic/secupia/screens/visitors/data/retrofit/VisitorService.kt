package com.anenigmatic.secupia.screens.visitors.data.retrofit

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

interface VisitorService {

    @GET("flat/guests")
    fun getExpectedVisitors(@Header("Authorization") jwt: String): Single<List<ExpectedVisitorFragment>>

    @POST("flat/guests")
    fun insertVisitor(@Header("Authorization") jwt: String, @Body body: RequestBody): Single<InsertVisitorResponse>

    @PATCH("flat/guests")
    fun updateVisitor(@Header("Authorization") jwt: String, @Body body: RequestBody): Completable

    @HTTP(method = "DELETE", path = "flat/guests", hasBody = true)
    fun deleteVisitor(@Header("Authorization") jwt: String, @Body body: RequestBody): Completable
}