package com.anenigmatic.secupia.screens.vehicle.data.retrofit

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface VehicleService {

    @GET("flat/vehicles")
    fun getAllVehicles(@Header("Authorization") jwt: String): Single<List<VehicleFragment>>

    @GET("flat/vehicles/{id}")
    fun getAllVehicleLogs(@Header("Authorization") jwt: String, @Path("id") id: String): Single<List<VehicleLogFragment>>
}