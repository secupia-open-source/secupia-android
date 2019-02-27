package com.anenigmatic.secupia.di.shared

import com.anenigmatic.secupia.di.home.HomeComponent
import com.anenigmatic.secupia.di.login.LoginComponent
import com.anenigmatic.secupia.di.vehicle.VehicleComponent
import com.anenigmatic.secupia.di.visitor.VisitorComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newLoginComponent(): LoginComponent

    fun newHomeComponent(): HomeComponent

    fun newVehicleComponent(): VehicleComponent

    fun newVisitorComponent(): VisitorComponent
}