package com.anenigmatic.secupia.screens.visitors.data.retrofit

import com.squareup.moshi.Json

data class ExpectedVisitorFragment(@field:Json(name = "id") val id: Long, @field:Json(name = "profile") val profile: ProfileFragment, @field:Json(name = "purpose") val purpose: String, @field:Json(name = "date_time") val datetime: String)

data class ProfileFragment(@field:Json(name = "name") val name: String, @field:Json(name = "contact") val registrationNo: String)