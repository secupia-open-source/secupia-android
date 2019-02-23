package com.anenigmatic.secupia.screens.login.core

sealed class UiOrder {

    object ShowLoadingState : UiOrder()

    object ShowWorkingState : UiOrder()

    object MoveToTheMainApp : UiOrder()
}