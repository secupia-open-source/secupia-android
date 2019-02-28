package com.anenigmatic.secupia.screens.vehicle.data.retrofit

import com.squareup.moshi.Json

data class VehicleFragment(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "license_plate") val registrationNo: String,
    @field:Json(name = "manufacturer") val manufacturer: String,
    @field:Json(name = "model") val model: String,
    @field:Json(name = "status") val status: String
)