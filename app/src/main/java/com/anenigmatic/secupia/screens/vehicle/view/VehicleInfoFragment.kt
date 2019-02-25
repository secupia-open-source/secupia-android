package com.anenigmatic.secupia.screens.vehicle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.vehicle.core.UiOrder
import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import com.anenigmatic.secupia.screens.vehicle.core.VehicleInfoViewModel
import com.anenigmatic.secupia.screens.vehicle.core.VehicleInfoViewModelFactory
import kotlinx.android.synthetic.main.fra_vehicle_info.view.*

class VehicleInfoFragment : Fragment(), VehiclesAdapter.ClickListener {

    private val viewModel by lazy {
        ViewModelProviders.of(this, VehicleInfoViewModelFactory())[VehicleInfoViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fra_vehicle_info, container, false)

        rootPOV.vehiclesRCY.adapter = VehiclesAdapter(this)

        rootPOV.refreshBTN.setOnClickListener {
            viewModel.refreshData()
        }

        viewModel.orderData.observe(viewLifecycleOwner, Observer { order ->
            when(order) {
                is UiOrder.ShowLoadingState -> showLoadingState()
                is UiOrder.ShowWorkingState -> showWorkingState(order.vehicles)
            }
        })

        viewModel.toastData.observe(viewLifecycleOwner, Observer { toast ->
            if(toast != null) {
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            }
        })

        return rootPOV
    }


    override fun onVehicleClicked(id: Long) {

    }


    private fun showLoadingState() {
        view?.let { view ->
            view.loaderPBR.visibility = View.VISIBLE

            view.refreshBTN.isClickable = false
            view.textView2.visibility = View.GONE
            view.vehiclesRCY.visibility = View.GONE
        }
    }

    private fun showWorkingState(vehicles: List<Vehicle>) {
        view?.let { view ->
            view.loaderPBR.visibility = View.GONE

            view.refreshBTN.isClickable = true
            view.textView2.visibility = View.VISIBLE
            view.vehiclesRCY.visibility = View.VISIBLE

            (view.vehiclesRCY.adapter as VehiclesAdapter).vehicles = vehicles
        }
    }
}