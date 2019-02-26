package com.anenigmatic.secupia.screens.vehicle.core

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anenigmatic.secupia.screens.shared.util.SingleLiveEvent
import com.anenigmatic.secupia.screens.shared.util.asMut
import com.anenigmatic.secupia.screens.shared.util.set
import com.anenigmatic.secupia.screens.vehicle.data.VehicleRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VehicleInfoViewModel(private val vRepo: VehicleRepository) : ViewModel() {

    sealed class UiOrder {

        object ShowLoadingState : UiOrder()

        data class ShowWorkingState(val vehicles: List<Vehicle>) : UiOrder()
    }


    val orderData: LiveData<UiOrder> = MutableLiveData()
    val toastData: LiveData<String?> = SingleLiveEvent()


    private val d1 = CompositeDisposable()


    init {
        loadData()
    }


    @SuppressLint("CheckResult")
    fun refreshData() {
        check(orderData.value is UiOrder.ShowWorkingState) { "Attempted to refresh data in non working state" }
        val oldOrder = orderData.value

        orderData.asMut().value = UiOrder.ShowLoadingState

        d1.clear()
        vRepo.refreshVehicleInfo()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    toastData.asMut().postValue("Data refreshed")
                    loadData()
                },
                {
                    orderData.asMut().postValue(oldOrder)
                    toastData.asMut().postValue("Couldn't refresh data")
                }
            )
    }


    private fun loadData() {
        d1.set(vRepo.getAllVehicles()
            .map { vehicles -> UiOrder.ShowWorkingState(vehicles) }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { order ->
                    orderData.asMut().postValue(order)
                },
                {
                    toastData.asMut().postValue("Something went wrong!")
                }
            ))
    }


    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}