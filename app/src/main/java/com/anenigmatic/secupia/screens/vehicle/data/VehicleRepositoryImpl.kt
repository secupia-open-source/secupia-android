package com.anenigmatic.secupia.screens.vehicle.data

import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLog
import com.anenigmatic.secupia.screens.vehicle.data.room.VehiclesDao
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class VehicleRepositoryImpl(private val vDao: VehiclesDao) : VehicleRepository {

    override fun getAllVehicles(): Flowable<List<Vehicle>> {
        return vDao.getAllVehicles()
    }

    override fun refreshVehicleInfo(): Completable {
        return Completable.complete()
            .delay(2, TimeUnit.SECONDS)
    }

    override fun getVehicleById(id: Long): Flowable<Vehicle> {
        return vDao.getVehicleById(id)
    }

    override fun getAllVehicleLogs(id: Long): Flowable<List<VehicleLog>> {
        return vDao.getAllVehicleLogs(id)
    }

    override fun refreshVehicleLogs(id: Long): Completable {
        return Completable.complete()
            .delay(2, TimeUnit.SECONDS)
    }
}