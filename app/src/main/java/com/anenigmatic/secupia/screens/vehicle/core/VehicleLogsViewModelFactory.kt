package com.anenigmatic.secupia.screens.vehicle.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anenigmatic.secupia.SecupiaApp
import com.anenigmatic.secupia.screens.vehicle.data.VehicleRepository
import javax.inject.Inject

class VehicleLogsViewModelFactory(private val id: Long) : ViewModelProvider.Factory {

    @Inject
    lateinit var vehicleRepository: VehicleRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        SecupiaApp.appComponent.newVehicleComponent().inject(this)
        return VehicleLogsViewModel(vehicleRepository, id) as T
    }
}