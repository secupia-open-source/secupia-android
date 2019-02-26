package com.anenigmatic.secupia.screens.vehicle.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLog
import io.reactivex.Flowable

@Dao
interface VehiclesDao {

    @Query("SELECT * FROM Vehicles")
    fun getAllVehicles(): Flowable<List<Vehicle>>

    @Query("SELECT * FROM Vehicles WHERE id = :id")
    fun getVehicleById(id: Long): Flowable<Vehicle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVehicles(vehicle: List<Vehicle>)

    @Query("DELETE FROM Vehicles")
    fun deleteAllVehicles()

    @Query("SELECT id, datetime, isEntryLog FROM VehicleLogs WHERE vehicleId = :id ORDER BY datetime DESC")
    fun getAllVehicleLogs(id: Long): Flowable<List<VehicleLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVehicleLogs(logs: List<VehicleLogTuple>)

    @Query("DELETE FROM VehicleLogs WHERE vehicleId = :id")
    fun deleteAllVehicleLogs(id: Long)
}