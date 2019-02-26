package com.anenigmatic.secupia.screens.home.core

sealed class UiOrder {

    object ShowLoadingState : UiOrder()

    object ShowWorkingState : UiOrder()

    object MoveToLogin : UiOrder()
}