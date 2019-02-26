package com.anenigmatic.secupia.screens.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.login.core.LoginViewModel
import com.anenigmatic.secupia.screens.login.core.LoginViewModelFactory
import com.anenigmatic.secupia.screens.login.core.UiOrder
import kotlinx.android.synthetic.main.fra_login.view.*

class LoginFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, LoginViewModelFactory())[LoginViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fra_login, container, false)

        rootPOV.loginBTN.setOnClickListener {
            viewModel.login(rootPOV.usernameTXT.text.toString(), rootPOV.passwordTXT.text.toString())
        }

        rootPOV.resetPasswordBTN.setOnClickListener {
            viewModel.resetPassword(rootPOV.usernameTXT.text.toString())
        }

        viewModel.order.observe(viewLifecycleOwner, Observer { order ->
            when(order) {
                is UiOrder.ShowLoadingState -> showLoadingState()
                is UiOrder.ShowWorkingState -> showWorkingState()
                is UiOrder.MoveToTheMainApp -> {
                    activity!!.supportFragmentManager.popBackStack()
                }
            }
        })

        viewModel.toast.observe(viewLifecycleOwner, Observer { toast ->
            if(toast != null) {
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            }
        })

        return rootPOV
    }


    private fun showLoadingState() {
        view?.let { view ->
            view.loaderPBR.visibility = View.VISIBLE

            view.domeIMG.visibility = View.GONE
            view.usernameHelperLBL.visibility = View.GONE
            view.usernameTXT.visibility = View.GONE
            view.passwordHelperLBL.visibility = View.GONE
            view.passwordTXT.visibility = View.GONE
            view.resetPasswordBTN.visibility = View.GONE
            view.loginBTN.visibility = View.GONE
        }
    }

    private fun showWorkingState() {
        view?.let { view ->
            view.loaderPBR.visibility = View.GONE

            view.domeIMG.visibility = View.VISIBLE
            view.usernameHelperLBL.visibility = View.VISIBLE
            view.usernameTXT.visibility = View.VISIBLE
            view.passwordHelperLBL.visibility = View.VISIBLE
            view.passwordTXT.visibility = View.VISIBLE
            view.resetPasswordBTN.visibility = View.VISIBLE
            view.loginBTN.visibility = View.VISIBLE
        }
    }
}