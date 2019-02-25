package com.anenigmatic.secupia.screens.vehicle.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import io.reactivex.Flowable

@Dao
interface VehiclesDao {

    @Query("SELECT * FROM Vehicles")
    fun getAllVehicles(): Flowable<List<Vehicle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVehicles(vehicle: List<Vehicle>)

    @Query("DELETE FROM Vehicles")
    fun deleteAllVehicles()
}