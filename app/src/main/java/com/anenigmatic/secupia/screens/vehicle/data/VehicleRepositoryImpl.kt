package com.anenigmatic.secupia.screens.vehicle.data

import com.anenigmatic.secupia.screens.shared.data.UserRepository
import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLog
import com.anenigmatic.secupia.screens.vehicle.data.retrofit.VehicleService
import com.anenigmatic.secupia.screens.vehicle.data.room.VehicleLogTuple
import com.anenigmatic.secupia.screens.vehicle.data.room.VehiclesDao
import io.reactivex.Completable
import io.reactivex.Flowable
import org.threeten.bp.LocalDateTime

class VehicleRepositoryImpl(
    private val uRepo: UserRepository,
    private val vDao: VehiclesDao,
    private val vService: VehicleService
) : VehicleRepository {

    override fun getAllVehicles(): Flowable<List<Vehicle>> {
        return vDao.getAllVehicles()
    }

    override fun refreshVehicleInfo(): Completable {
        return uRepo.getUser()
            .firstOrError()
            .flatMap { user ->
                vService.getAllVehicles(user.jwt)
                    .map { response ->
                        response.map {
                            val isInside = when(it.status) {
                                "In" -> true
                                else -> false
                            }
                            Vehicle(it.id, it.registrationNo, it.manufacturer + it.model, isInside)
                        }
                    }
                    .doOnSuccess { vehicles ->
                        vDao.deleteAllVehicles()
                        vDao.insertVehicles(vehicles)
                    }
            }
            .ignoreElement()
    }

    override fun getVehicleById(id: Long): Flowable<Vehicle> {
        return vDao.getVehicleById(id)
    }

    override fun getAllVehicleLogs(id: Long): Flowable<List<VehicleLog>> {
        return vDao.getAllVehicleLogs(id)
    }

    override fun refreshVehicleLogs(id: Long): Completable {
        return uRepo.getUser()
            .firstOrError()
            .flatMap { user ->
                vService.getAllVehicleLogs(user.jwt, id.toString())
                    .map { response ->
                        response.map {
                            VehicleLogTuple(it.id, it.vehicleId, LocalDateTime.parse(it.datetime.dropLast(8)), it.isEntryLog)
                        }
                    }
            }
            .doOnSuccess { vehicleLogs ->
                vDao.deleteAllVehicleLogs(id)
                vDao.insertVehicleLogs(vehicleLogs)
            }
            .ignoreElement()
    }
}