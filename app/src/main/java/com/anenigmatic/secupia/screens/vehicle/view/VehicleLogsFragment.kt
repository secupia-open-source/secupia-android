package com.anenigmatic.secupia.screens.vehicle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLog
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLogsViewModel
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLogsViewModel.UiOrder
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLogsViewModelFactory
import kotlinx.android.synthetic.main.fra_vehicle_logs.view.*

class VehicleLogsFragment : Fragment() {

    private val viewModel by lazy {
        val id = arguments!!.getLong("ID")
        ViewModelProviders.of(this, VehicleLogsViewModelFactory(id))[VehicleLogsViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fra_vehicle_logs, container, false)

        rootPOV.vehicleLogsRCY.adapter = VehicleLogsAdapter()

        rootPOV.backBTN.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }

        rootPOV.refreshBTN.setOnClickListener {
            viewModel.refresh()
        }

        rootPOV.filter03DaysBTN.setOnClickListener {
            viewModel.useAgeFilterAndLoadData(3)
        }

        rootPOV.filter07DaysBTN.setOnClickListener {
            viewModel.useAgeFilterAndLoadData(7)
        }

        rootPOV.filter15DaysBTN.setOnClickListener {
            viewModel.useAgeFilterAndLoadData(15)
        }

        viewModel.orderData.observe(viewLifecycleOwner, Observer { order ->
            when (order) {
                is UiOrder.ShowLoadingState -> showLoadingState()
                is UiOrder.ShowWorkingState -> showWorkingState(order.ageFilter, order.vehicle, order.logs)
            }
        })

        viewModel.toastData.observe(viewLifecycleOwner, Observer { toast ->
            if (toast != null) {
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            }
        })

        return rootPOV
    }


    private fun showLoadingState() {
        view?.let { view ->
            view.loaderPBR.visibility = View.VISIBLE

            view.textView7.visibility = View.GONE
            view.filter03DaysBTN.visibility = View.GONE
            view.filter07DaysBTN.visibility = View.GONE
            view.filter15DaysBTN.visibility = View.GONE
            view.vehicleLogsRCY.visibility = View.GONE
        }
    }

    private fun showWorkingState(ageFilter: Int, vehicle: Vehicle, logs: List<VehicleLog>) {
        view?.let { view ->
            view.loaderPBR.visibility = View.GONE

            view.textView7.visibility = View.VISIBLE
            view.filter03DaysBTN.visibility = View.VISIBLE
            view.filter07DaysBTN.visibility = View.VISIBLE
            view.filter15DaysBTN.visibility = View.VISIBLE
            view.vehicleLogsRCY.visibility = View.VISIBLE

            view.screenTitleLBL.text = vehicle.name
            view.registrationNoLBL.text = vehicle.registrationNo

            when(ageFilter) {
                3  -> {
                    view.filter03DaysBTN.isSelected = true
                    view.filter07DaysBTN.isSelected = false
                    view.filter15DaysBTN.isSelected = false

                    view.filter03DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.wht01))
                    view.filter07DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.blu01))
                    view.filter15DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.blu01))
                }
                7  -> {
                    view.filter03DaysBTN.isSelected = false
                    view.filter07DaysBTN.isSelected = true
                    view.filter15DaysBTN.isSelected = false

                    view.filter03DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.blu01))
                    view.filter07DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.wht01))
                    view.filter15DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.blu01))
                }
                15 -> {
                    view.filter03DaysBTN.isSelected = false
                    view.filter07DaysBTN.isSelected = false
                    view.filter15DaysBTN.isSelected = true

                    view.filter03DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.blu01))
                    view.filter07DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.blu01))
                    view.filter15DaysBTN.setTextColor(ContextCompat.getColor(requireContext(), R.color.wht01))
                }
            }

            (view.vehicleLogsRCY.adapter as VehicleLogsAdapter).vehicleLogs = logs
        }
    }
}