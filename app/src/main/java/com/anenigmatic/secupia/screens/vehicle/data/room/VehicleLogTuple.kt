package com.anenigmatic.secupia.screens.vehicle.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity(tableName = "VehicleLogs")
data class VehicleLogTuple(
    @PrimaryKey val id: Long,
    val vehicleId: Long,
    val datetime: LocalDateTime,
    val isEntryLog: Boolean
)