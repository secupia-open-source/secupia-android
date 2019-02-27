package com.anenigmatic.secupia.screens.shared.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import com.anenigmatic.secupia.screens.vehicle.data.room.VehicleLogTuple
import com.anenigmatic.secupia.screens.vehicle.data.room.VehiclesDao
import com.anenigmatic.secupia.screens.visitors.core.Visitor
import com.anenigmatic.secupia.screens.visitors.data.room.VisitorsDao

@Database(entities = [Vehicle::class, VehicleLogTuple::class, Visitor::class], version = 1, exportSchema = true)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getVehiclesDao(): VehiclesDao

    abstract fun getVisitorsDao(): VisitorsDao
}