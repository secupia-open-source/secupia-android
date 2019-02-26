package com.anenigmatic.secupia.screens.vehicle.core

import org.threeten.bp.LocalDateTime

/**
 * Represents a log of a vehicle. Logs capture details about when a particular vehicle
 * entered or exited a society.
 *
 * @property id  is the id of the log.
 * @property isEntryLog  tells whether the vehicle entered the society (in which case,
 *      it is true) or exited the society(in which case, it is false)
 * */
data class VehicleLog(val id: Long, val datetime: LocalDateTime, val isEntryLog: Boolean)