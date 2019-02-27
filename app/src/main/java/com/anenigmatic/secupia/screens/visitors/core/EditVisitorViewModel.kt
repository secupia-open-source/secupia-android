package com.anenigmatic.secupia.screens.visitors.core

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anenigmatic.secupia.screens.shared.util.SingleLiveEvent
import com.anenigmatic.secupia.screens.shared.util.asMut
import com.anenigmatic.secupia.screens.visitors.data.VisitorRepository
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

class EditVisitorViewModel(private val vRepo: VisitorRepository, private val id: Long?) : ViewModel() {

    sealed class UiOrder {

        object ShowLoadingState : UiOrder()

        data class ShowWorkingState(val visitor: Visitor) : UiOrder()

        object MoveToVisitorList : UiOrder()
    }


    val orderData: LiveData<UiOrder> = MutableLiveData()
    val toastData: LiveData<String?> = SingleLiveEvent()


    init {
        if(id != null) {
            vRepo.getVisitorById(id)
                .take(1)
                .map { visitor -> UiOrder.ShowWorkingState(visitor) }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { order ->
                        orderData.asMut().postValue(order)
                    },
                    {
                        toastData.asMut().postValue("Something went wrong :/")
                    }
                )
        }
    }


    @SuppressLint("CheckResult")
    fun onPositiveAction(name: String, dateString: String, timeString: String, regNo: String, purpose: String) {
        if(!isInputValid(name, dateString, timeString, regNo, purpose)) {
            return
        }

        val date = LocalDate.of(dateString.takeLast(4).toInt(), dateString.substring(3..4).toInt(), dateString.take(2).toInt())
        val time = LocalTime.of(timeString.take(2).toInt(), timeString.takeLast(2).toInt())

        val backupState = if(orderData.value is UiOrder.ShowWorkingState) {
            orderData.value
        } else {
            UiOrder.ShowWorkingState(Visitor(id?: 0, "", LocalDateTime.now(), "", ""))
        }

        orderData.asMut().value = UiOrder.ShowLoadingState

        if(id == null) {
            vRepo.insertVisitor(Visitor(0L, name, LocalDateTime.of(date, time), regNo, purpose))
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        toastData.asMut().postValue("Inserted visitor data")
                        orderData.asMut().postValue(UiOrder.MoveToVisitorList)
                    },
                    {
                        toastData.asMut().postValue("Something went wrong :/")
                        orderData.asMut().postValue(backupState)
                    }
                )
        } else {
            vRepo.updateVisitor(Visitor(id, name, LocalDateTime.of(date, time), regNo, purpose))
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        toastData.asMut().postValue("Updated visitor data")
                        orderData.asMut().postValue(UiOrder.MoveToVisitorList)
                    },
                    {
                        toastData.asMut().postValue("Something went wrong :/")
                        orderData.asMut().postValue(backupState)
                    }
                )
        }
    }

    fun onNegativeAction() {
        if(id == null) {
            orderData.asMut().value = UiOrder.MoveToVisitorList
            return
        }

        val backupState = if(orderData.value is UiOrder.ShowWorkingState) {
            orderData.value
        } else {
            UiOrder.ShowWorkingState(Visitor(id, "", LocalDateTime.now(), "", ""))
        }

        orderData.asMut().value = UiOrder.ShowLoadingState

        vRepo.deleteVisitorById(id)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    toastData.asMut().postValue("Deleted visitor data")
                    orderData.asMut().postValue(UiOrder.MoveToVisitorList)
                },
                {
                    toastData.asMut().postValue("Something went wrong :/")
                    orderData.asMut().postValue(backupState)
                }
            )
    }


    private fun isInputValid(name: String, dateString: String, timeString: String, regNo: String, purpose: String): Boolean {
        if(name.isBlank()) {
            toastData.asMut().value = "You can't leave the name field empty"
            return false
        }
        if(!Regex("""\d{2}/\d{2}/\d{4}""").matches(dateString)) {
            toastData.asMut().value = "Please enter date in given format"
            return false
        }
        if(!Regex("""\d{2}:\d{2}""").matches(timeString)) {
            toastData.asMut().value = "Please enter time in given format"
            return false
        }
        if(!Regex("""\D{2} \d{2} \D{2} \d{4}""").matches(regNo)) {
            toastData.asMut().value = "Please enter registration number in correct format"
            return false
        }
        return true
    }
}