package com.anenigmatic.secupia.di.vehicle

import com.anenigmatic.secupia.screens.vehicle.core.VehicleInfoViewModelFactory
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLogsViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [])
interface VehicleComponent {

    fun inject(factory: VehicleInfoViewModelFactory)

    fun inject(factory: VehicleLogsViewModelFactory)
}