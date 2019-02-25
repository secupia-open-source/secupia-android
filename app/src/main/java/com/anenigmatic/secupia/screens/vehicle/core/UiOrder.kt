package com.anenigmatic.secupia.screens.vehicle.core

sealed class UiOrder {

    object ShowLoadingState : UiOrder()

    data class ShowWorkingState(val vehicles: List<Vehicle>) : UiOrder()
}