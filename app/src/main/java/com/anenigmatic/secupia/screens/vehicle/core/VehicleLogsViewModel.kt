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
import org.threeten.bp.LocalDateTime

class VehicleLogsViewModel(private val vRepo: VehicleRepository, private val id: Long) : ViewModel() {

    sealed class UiOrder {

        object ShowLoadingState : UiOrder()

        data class ShowWorkingState(val ageFilter: Int, val vehicle: Vehicle, val logs: List<VehicleLog>) : UiOrder()
    }


    val orderData: LiveData<UiOrder> = MutableLiveData()
    val toastData: LiveData<String?> = SingleLiveEvent()


    // We show logs which are less than ageFilter days old.
    private var ageFilter = 3


    private val d1 = CompositeDisposable()


    init {
        useAgeFilterAndLoadData(3)
    }


    fun useAgeFilterAndLoadData(days: Int) {
        ageFilter = days

        d1.set(vRepo.getVehicleById(id)
            .flatMap { vehicle ->
                vRepo.getAllVehicleLogs(id)
                    .map { logs -> logs.filter { log -> LocalDateTime.now().minusDays(days.toLong()) < log.datetime } }
                    .map { logs -> UiOrder.ShowWorkingState(days, vehicle, logs) }
            }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { order ->
                    orderData.asMut().postValue(order)
                },
                {
                    toastData.asMut().postValue("An internal error occurred :/")
                }
            ))
    }

    @SuppressLint("CheckResult")
    fun refresh() {
        check(orderData.value is UiOrder.ShowWorkingState) { "Attempted to refresh data in non working state" }
        val oldOrder = orderData.value

        orderData.asMut().value = UiOrder.ShowLoadingState

        d1.clear()
        vRepo.refreshVehicleLogs(id)
            .subscribeOn(Schedulers.io())
            .subscribe(
            {
                toastData.asMut().postValue("Data refreshed")
                useAgeFilterAndLoadData(ageFilter)
            },
            {
                orderData.asMut().postValue(oldOrder)
                toastData.asMut().postValue("Couldn't refresh data")
            }
        )
    }


    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}