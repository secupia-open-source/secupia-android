package com.anenigmatic.secupia.di.shared

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.anenigmatic.secupia.screens.shared.data.UserRepository
import com.anenigmatic.secupia.screens.shared.data.UserRepositoryImpl
import com.anenigmatic.secupia.screens.shared.data.room.AppDatabase
import com.anenigmatic.secupia.screens.vehicle.data.VehicleRepository
import com.anenigmatic.secupia.screens.vehicle.data.VehicleRepositoryImpl
import com.anenigmatic.secupia.screens.vehicle.data.room.VehiclesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun providesVehicleRepository(vehiclesDao: VehiclesDao): VehicleRepository {
        return VehicleRepositoryImpl(vehiclesDao)
    }

    @Singleton
    @Provides
    fun providesUserRepository(sharedPreferences: SharedPreferences): UserRepository {
        return UserRepositoryImpl(sharedPreferences)
    }

    @Singleton
    @Provides
    fun providesVehiclesDao(appDatabase: AppDatabase): VehiclesDao {
        return appDatabase.getVehiclesDao()
    }

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "secupia.db")
            .build()
    }

    @Singleton
    @Provides
    fun providesSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("secupia.sp", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providesApplication(): Application {
        return application
    }
}