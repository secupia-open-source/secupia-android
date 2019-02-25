package com.anenigmatic.secupia.di.vehicle

import com.anenigmatic.secupia.screens.vehicle.core.VehicleInfoViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [])
interface VehicleComponent {

    fun inject(factory: VehicleInfoViewModelFactory)
}