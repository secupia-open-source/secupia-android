package com.anenigmatic.secupia.screens.visitors.core

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anenigmatic.secupia.screens.shared.util.SingleLiveEvent
import com.anenigmatic.secupia.screens.shared.util.asMut
import com.anenigmatic.secupia.screens.shared.util.set
import com.anenigmatic.secupia.screens.visitors.data.VisitorRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VisitorListViewModel(private val vRepo: VisitorRepository) : ViewModel() {

    sealed class UiOrder {

        object ShowLoadingState : UiOrder()

        data class ShowWorkingState(val visitors: List<Visitor>) : UiOrder()
    }


    val orderData: LiveData<UiOrder> = MutableLiveData()
    val toastData: LiveData<String?> = SingleLiveEvent()


    private val d1 = CompositeDisposable()


    init {
        loadData()
    }


    @SuppressLint("CheckResult")
    fun refresh() {
        check(orderData.value is UiOrder.ShowWorkingState) { "Attempted to refresh data in non working state" }
        val oldOrder = orderData.value

        orderData.asMut().value = UiOrder.ShowLoadingState

        d1.clear()
        vRepo.refreshVisitors()
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
        val backupState = if(orderData.value is UiOrder.ShowWorkingState) {
            orderData.value
        } else {
            UiOrder.ShowWorkingState(listOf())
        }

        orderData.asMut().postValue(UiOrder.ShowLoadingState)

        d1.set(vRepo.getAllVisitors()
            .map { visitors -> UiOrder.ShowWorkingState(visitors) }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { order ->
                    orderData.asMut().postValue(order)
                },
                {
                    orderData.asMut().postValue(backupState)
                    toastData.asMut().postValue("Something went wrong :/")
                }
            ))
    }


    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}