package com.anenigmatic.secupia.di.login

import com.anenigmatic.secupia.screens.login.core.LoginViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [])
interface LoginComponent {

    fun inject(factory: LoginViewModelFactory)
}