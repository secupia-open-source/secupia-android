package com.anenigmatic.secupia.screens.vehicle.data

import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLog
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Used for accessing the info related to the vehicles owned by the user.
 * */
interface VehicleRepository {

    /**
     * Gives a list of all vehicles owned by the user.
     * */
    fun getAllVehicles(): Flowable<List<Vehicle>>

    /**
     * Gives the vehicle  which  has the passed-in id.
     * */
    fun getVehicleById(id: Long): Flowable<Vehicle>

    /**
     * Makes the app get vehicle related info from the backend.
     * */
    fun refreshVehicleInfo(): Completable

    /**
     * Gives a list  of  all  logs  associated with the vehicle
     * having the passed-in id. They're in  descending order of
     * datetime.
     * */
    fun getAllVehicleLogs(id: Long): Flowable<List<VehicleLog>>

    /**
     * Makes the app get  vehicle logs for the vehicle with the
     * passed-in id from the backed.
     * */
    fun refreshVehicleLogs(id: Long): Completable
}