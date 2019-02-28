package com.anenigmatic.secupia.screens.vehicle.data.retrofit

import com.squareup.moshi.Json

data class VehicleLogFragment(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "vehicle") val vehicleId: Long,
    @field:Json(name = "date_time") val datetime: String,
    @field:Json(name = "is_entry") val isEntryLog: Boolean
)