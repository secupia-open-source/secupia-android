package com.anenigmatic.secupia.screens.vehicle.core

/**
 * Represents a vehicle owned by the user.
 *
 * @property name  is the name of the vehicle(eg: Tesla Roadster, Audi Quattro).
 * @property isInside  tells whether  the vehicle is inside  the society or not.
 * */
data class Vehicle(
    val id: Long,
    val registrationNo: String,
    val name: String,
    val isInside: Boolean
)