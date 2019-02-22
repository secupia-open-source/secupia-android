package com.anenigmatic.secupia.screens.shared.core

/**
 * Represents a user of the app.
 *
 * @property jwt  is the JWT(JSON Web Token) associated with the user.
 * @property name  is the user's  full  name.
 * @property flat  is the user's fully qualified address which includes block no, floor,
 *      flat no, etc wherever applicable.
 * @property vehicleNumbers  is the list of registration numbers of the user's vehicles.
 * */
data class User(
    val jwt: String,
    val name: String,
    val flat: String,
    val vehicleNumbers: List<String> = listOf()
)