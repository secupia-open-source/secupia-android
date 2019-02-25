package com.anenigmatic.secupia.screens.vehicle.core

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a vehicle owned by the user.
 *
 * @property name  is the name of the vehicle(eg: Tesla Roadster, Audi Quattro).
 * @property isInside  tells whether  the vehicle is inside  the society or not.
 * */
@Entity(tableName = "Vehicles")
data class Vehicle(
    @PrimaryKey val id: Long,
    val registrationNo: String,
    val name: String,
    val isInside: Boolean
)