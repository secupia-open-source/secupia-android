package com.anenigmatic.secupia.screens.login.core

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anenigmatic.secupia.screens.shared.data.UserRepository
import com.anenigmatic.secupia.screens.shared.util.SingleLiveEvent
import com.anenigmatic.secupia.screens.shared.util.asMut
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val uRepo: UserRepository) : ViewModel() {

    sealed class UiOrder {

        object ShowLoadingState : UiOrder()

        object ShowWorkingState : UiOrder()

        object MoveToTheMainApp : UiOrder()
    }


    val order: LiveData<UiOrder> = MutableLiveData()
    val toast: LiveData<String?> = SingleLiveEvent()


    init {
        order.asMut().value = UiOrder.ShowWorkingState
    }


    @SuppressLint("CheckResult")
    fun login(username: String, password: String) {
        if(username.isBlank()) {
            toast.asMut().value = "Username cannot be blank"
            return
        }
        if(password.isBlank()) {
            toast.asMut().value = "Password cannot be blank"
            return
        }

        order.asMut().value = UiOrder.ShowLoadingState

        uRepo.login(username, password)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    toast.asMut().postValue("Login successful!")
                    order.asMut().postValue(UiOrder.MoveToTheMainApp)
                },
                {
                    toast.asMut().postValue("Couldn't login :/")
                    order.asMut().postValue(UiOrder.ShowWorkingState)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun resetPassword(username: String) {
        if(username.isBlank()) {
            toast.asMut().value = "Please enter you username to reset your password"
            return
        }

        order.asMut().value = UiOrder.ShowLoadingState
        uRepo.resetPassword(username)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    toast.asMut().postValue("An email to reset your password has been sent to your inbox")
                    order.asMut().postValue(UiOrder.MoveToTheMainApp)
                },
                {
                    toast.asMut().postValue("An error occurred. Couldn't reset your password. Try again!")
                    order.asMut().postValue(UiOrder.ShowWorkingState)
                }
            )
    }
}