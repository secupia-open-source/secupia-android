package com.anenigmatic.secupia.screens.vehicle.data

import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
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
     * Makes the app get vehicle related info from the backend.
     * */
    fun refreshVehicleInfo(): Completable
}