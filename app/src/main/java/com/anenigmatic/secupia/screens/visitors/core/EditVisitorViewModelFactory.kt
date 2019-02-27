package com.anenigmatic.secupia.screens.visitors.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anenigmatic.secupia.SecupiaApp
import com.anenigmatic.secupia.screens.visitors.data.VisitorRepository
import javax.inject.Inject

class EditVisitorViewModelFactory(private val id: Long?) : ViewModelProvider.Factory {

    @Inject
    lateinit var visitorRepository: VisitorRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        SecupiaApp.appComponent.newVisitorComponent().inject(this)
        return EditVisitorViewModel(visitorRepository, id) as T
    }
}