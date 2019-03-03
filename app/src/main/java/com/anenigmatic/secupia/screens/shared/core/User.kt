package com.anenigmatic.secupia.screens.shared.core

/**
 * Represents a user of the app.
 *
 * @property jwt  is the JWT(JSON Web Token) associated with the user.
 * */
data class User(
    val jwt: String
)