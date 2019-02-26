package com.anenigmatic.secupia.screens.home.core

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anenigmatic.secupia.screens.shared.data.UserRepository
import com.anenigmatic.secupia.screens.shared.util.SingleLiveEvent
import com.anenigmatic.secupia.screens.shared.util.asMut
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val uRepo: UserRepository) : ViewModel() {

    val orderData: LiveData<UiOrder> = MutableLiveData()
    val toastData: LiveData<String?> = SingleLiveEvent()


    fun checkLoginStatus() {
        orderData.asMut().value = UiOrder.ShowLoadingState

        uRepo.getUser(false)
            .firstOrError()
            .ignoreElement()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    orderData.asMut().postValue(UiOrder.ShowWorkingState)
                },
                {
                    toastData.asMut().postValue("Please login to use the app")
                    orderData.asMut().postValue(UiOrder.MoveToLogin)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun logout() {
        orderData.asMut().value = UiOrder.ShowLoadingState

        uRepo.logout()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    orderData.asMut().postValue(UiOrder.MoveToLogin)
                },
                {
                    orderData.asMut().postValue(UiOrder.ShowWorkingState)
                    toastData.asMut().postValue("Couldn't logout :/")
                }
            )
    }
}