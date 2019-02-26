package com.anenigmatic.secupia.screens.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.home.core.HomeViewModel
import com.anenigmatic.secupia.screens.home.core.HomeViewModelFactory
import com.anenigmatic.secupia.screens.home.core.UiOrder
import com.anenigmatic.secupia.screens.login.view.LoginFragment
import com.anenigmatic.secupia.screens.vehicle.view.VehicleInfoFragment
import kotlinx.android.synthetic.main.fra_home.view.*

class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, HomeViewModelFactory())[HomeViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fra_home, container, false)

        viewModel.checkLoginStatus()

        rootPOV.showVehicleLogBTN.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.rootPOV, VehicleInfoFragment())
                .addToBackStack(null)
                .commit()
        }

        rootPOV.logoutBTN.setOnClickListener {
            viewModel.logout()
        }

        viewModel.orderData.observe(viewLifecycleOwner, Observer { order ->
            when(order) {
                is UiOrder.ShowLoadingState -> showLoadingState()
                is UiOrder.ShowWorkingState -> showWorkingState()
                is UiOrder.MoveToLogin      -> {
                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.rootPOV, LoginFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        })

        viewModel.toastData.observe(viewLifecycleOwner, Observer { toast ->
            if(toast != null) {
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            }
        })

        return rootPOV
    }


    private fun showLoadingState() {
        view?.let { view ->
            view.loaderPBR.visibility = View.VISIBLE
            view.contentSCL.visibility = View.GONE
        }
    }

    private fun showWorkingState() {
        view?.let { view ->
            view.loaderPBR.visibility = View.GONE
            view.contentSCL.visibility = View.VISIBLE
        }
    }
}