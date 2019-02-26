package com.anenigmatic.secupia.di.home

import com.anenigmatic.secupia.screens.home.core.HomeViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [])
interface HomeComponent {

    fun inject(factory: HomeViewModelFactory)
}