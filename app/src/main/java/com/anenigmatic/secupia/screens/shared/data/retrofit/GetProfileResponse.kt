package com.anenigmatic.secupia.screens.shared.data.retrofit

import com.squareup.moshi.Json

data class GetProfileResponse(
    @field:Json(name = "profile") val profile: ProfileFragment,
    @field:Json(name = "vehicle") val vehicles: List<VehicleFragment>
)

data class ProfileFragment(@field:Json(name = "name") val name: String, @field:Json(name = "addr") val flat: String)

data class VehicleFragment(@field:Json(name = "license_plate") val registrationNo: String)