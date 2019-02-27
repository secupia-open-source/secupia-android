package com.anenigmatic.secupia.screens.shared.data.retrofit

import com.squareup.moshi.Json

data class LoginResponse(@field:Json(name = "token") val jwt: String)