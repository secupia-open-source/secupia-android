package com.anenigmatic.secupia.screens.home.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anenigmatic.secupia.SecupiaApp
import com.anenigmatic.secupia.screens.shared.data.UserRepository
import javax.inject.Inject

class HomeViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var userRepository: UserRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        SecupiaApp.appComponent.newHomeComponent().inject(this)
        return HomeViewModel(userRepository) as T
    }
}