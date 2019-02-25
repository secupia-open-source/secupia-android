package com.anenigmatic.secupia.screens.shared.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import com.anenigmatic.secupia.screens.vehicle.data.room.VehiclesDao

@Database(entities = [Vehicle::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getVehiclesDao(): VehiclesDao
}