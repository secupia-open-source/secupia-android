package com.anenigmatic.secupia.screens.visitors.core

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

/**
 * Represents a visitor.
 *
 * @property vehicleRegistrationNo  is the registration number of the visitor's car. If
 *      it is null, it means either that the user didn't come via a vehicle or there is
 *      no information about it's registration number.
 * */
@Entity(tableName = "Visitors")
data class Visitor(
    @PrimaryKey val id: Long,
    val name: String,
    val datetime: LocalDateTime,
    val vehicleRegistrationNo: String?,
    val purposeOfVisit: String
)