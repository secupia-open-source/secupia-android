package com.anenigmatic.secupia.di.shared

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.anenigmatic.secupia.screens.shared.data.UserRepository
import com.anenigmatic.secupia.screens.shared.data.UserRepositoryImpl
import com.anenigmatic.secupia.screens.shared.data.retrofit.BaseInterceptor
import com.anenigmatic.secupia.screens.shared.data.retrofit.UserService
import com.anenigmatic.secupia.screens.shared.data.room.AppDatabase
import com.anenigmatic.secupia.screens.vehicle.data.VehicleRepository
import com.anenigmatic.secupia.screens.vehicle.data.VehicleRepositoryImpl
import com.anenigmatic.secupia.screens.vehicle.data.room.VehiclesDao
import com.anenigmatic.secupia.screens.visitors.data.VisitorRepository
import com.anenigmatic.secupia.screens.visitors.data.VisitorRepositoryImpl
import com.anenigmatic.secupia.screens.visitors.data.room.VisitorsDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun providesVisitorRepository(visitorsDao: VisitorsDao): VisitorRepository {
        return VisitorRepositoryImpl(visitorsDao)
    }

    @Singleton
    @Provides
    fun providesVehicleRepository(vehiclesDao: VehiclesDao): VehicleRepository {
        return VehicleRepositoryImpl(vehiclesDao)
    }

    @Singleton
    @Provides
    fun providesUserRepository(sharedPreferences: SharedPreferences, userService: UserService): UserRepository {
        return UserRepositoryImpl(sharedPreferences, userService)
    }

    @Singleton
    @Provides
    fun providesUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.43.47:8000/api/")
            .client(OkHttpClient().newBuilder().addInterceptor(BaseInterceptor()).build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesVisitorsDao(appDatabase: AppDatabase): VisitorsDao {
        return appDatabase.getVisitorsDao()
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