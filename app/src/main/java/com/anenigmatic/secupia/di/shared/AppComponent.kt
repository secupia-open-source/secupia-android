package com.anenigmatic.secupia.di.shared

import com.anenigmatic.secupia.di.login.LoginComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newLoginComponent(): LoginComponent
}