package com.anenigmatic.secupia.screens.visitors.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.shared.util.prepadWithZero
import com.anenigmatic.secupia.screens.visitors.core.EditVisitorViewModel
import com.anenigmatic.secupia.screens.visitors.core.EditVisitorViewModel.UiOrder
import com.anenigmatic.secupia.screens.visitors.core.EditVisitorViewModelFactory
import com.anenigmatic.secupia.screens.visitors.core.Visitor
import kotlinx.android.synthetic.main.fra_edit_visitor.view.*

class EditVisitorFragment : Fragment() {

    private val id by lazy {
        val temp = arguments!!.getLong("ID", -1)
        if(temp == -1L) {
            null
        } else {
            temp
        }
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, EditVisitorViewModelFactory(id))[EditVisitorViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fra_edit_visitor, container, false)

        if(id == null) {
            rootPOV.positiveBTN.text = "Add"
            rootPOV.negativeBTN.text = "Cancel"
        } else {
            rootPOV.positiveBTN.text = "Update"
            rootPOV.negativeBTN.text = "Delete"
        }

        rootPOV.backBTN.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }

        rootPOV.positiveBTN.setOnClickListener {
            val name = rootPOV.nameTXT.text.toString()
            val dateString = rootPOV.dateTXT.text.toString()
            val timeString = rootPOV.timeTXT.text.toString()
            val phoneNo = rootPOV.phoneNoTXT.text.toString()
            val purpose = rootPOV.purposeTXT.text.toString()
            viewModel.onPositiveAction(name, dateString, timeString, phoneNo, purpose)
        }

        rootPOV.negativeBTN.setOnClickListener {
            viewModel.onNegativeAction()
        }

        viewModel.orderData.observe(viewLifecycleOwner, Observer { order ->
            when(order) {
                is UiOrder.ShowLoadingState  -> showLoadingState()
                is UiOrder.ShowWorkingState  -> showWorkingState(order.visitor)
                is UiOrder.MoveToVisitorList -> {
                    activity!!.supportFragmentManager.popBackStack()
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

            view.textView.visibility = View.GONE
            view.nameTXT.visibility = View.GONE
            view.textView8.visibility = View.GONE
            view.dateTXT.visibility = View.GONE
            view.textView10.visibility = View.GONE
            view.timeTXT.visibility = View.GONE
            view.textView11.visibility = View.GONE
            view.phoneNoTXT.visibility = View.GONE
            view.textView14.visibility = View.GONE
            view.purposeTXT.visibility = View.GONE
            view.positiveBTN.visibility = View.GONE
            view.negativeBTN.visibility = View.GONE
        }
    }

    private fun showWorkingState(visitor: Visitor) {
        view?.let { view ->
            view.loaderPBR.visibility = View.GONE

            view.textView.visibility = View.VISIBLE
            view.nameTXT.visibility = View.VISIBLE
            view.textView8.visibility = View.VISIBLE
            view.dateTXT.visibility = View.VISIBLE
            view.textView10.visibility = View.VISIBLE
            view.timeTXT.visibility = View.VISIBLE
            view.textView11.visibility = View.VISIBLE
            view.phoneNoTXT.visibility = View.VISIBLE
            view.textView14.visibility = View.VISIBLE
            view.purposeTXT.visibility = View.VISIBLE
            view.positiveBTN.visibility = View.VISIBLE
            view.negativeBTN.visibility = View.VISIBLE

            view.nameTXT.setText(visitor.name)
            val date = "${visitor.datetime.dayOfMonth.prepadWithZero()}/${visitor.datetime.monthValue.prepadWithZero()}/${visitor.datetime.year}"
            view.dateTXT.setText(date)
            view.timeTXT.setText(visitor.datetime.toLocalTime().toString().take(5))
            view.phoneNoTXT.setText(visitor.phoneNo)
            view.purposeTXT.setText(visitor.purposeOfVisit)
        }
    }
}