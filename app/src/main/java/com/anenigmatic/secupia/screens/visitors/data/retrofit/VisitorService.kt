package com.anenigmatic.secupia.screens.visitors.data.retrofit

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

interface VisitorService {

    @GET("resident/guest")
    fun getExpectedVisitors(@Header("Authorization") jwt: String): Single<List<ExpectedVisitorFragment>>

    @POST("resident/guest")
    fun insertVisitor(@Header("Authorization") jwt: String, @Body body: RequestBody): Single<InsertVisitorResponse>

    @PATCH("resident/guest")
    fun updateVisitor(@Header("Authorization") jwt: String, @Body body: RequestBody): Completable

    @HTTP(method = "DELETE", path = "resident/guest", hasBody = true)
    fun deleteVisitor(@Header("Authorization") jwt: String, @Body body: RequestBody): Completable
}