package com.anenigmatic.secupia.screens.visitors.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.visitors.core.Visitor
import com.anenigmatic.secupia.screens.visitors.core.VisitorListViewModel
import com.anenigmatic.secupia.screens.visitors.core.VisitorListViewModel.UiOrder
import com.anenigmatic.secupia.screens.visitors.core.VisitorListViewModelFactory
import kotlinx.android.synthetic.main.fra_visitor_list.view.*

class VisitorListFragment : Fragment(), VisitorsAdapter.ClickListener {

    private val viewModel by lazy {
        ViewModelProviders.of(this, VisitorListViewModelFactory())[VisitorListViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fra_visitor_list, container, false)

        rootPOV.visitorsRCY.adapter = VisitorsAdapter(this)

        rootPOV.backBTN.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }

        rootPOV.refreshBTN.setOnClickListener {
            viewModel.refresh()
        }

        rootPOV.addVisitorBTN.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.rootPOV, EditVisitorFragment().also { it.arguments = bundleOf("ID" to -1) })
                .addToBackStack(null)
                .commit()
        }

        viewModel.orderData.observe(viewLifecycleOwner, Observer { order ->
            when(order) {
                is UiOrder.ShowLoadingState -> showLoadingState()
                is UiOrder.ShowWorkingState -> showWorkingState(order.visitors)
            }
        })

        viewModel.toastData.observe(viewLifecycleOwner, Observer { toast ->
            if(toast != null) {
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            }
        })

        return rootPOV
    }


    override fun onEditVisitorAction(id: Long) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.rootPOV, EditVisitorFragment().also { it.arguments = bundleOf("ID" to id) })
            .addToBackStack(null)
            .commit()
    }


    private fun showLoadingState() {
        view?.let { view ->
            view.loaderPBR.visibility = View.VISIBLE

            view.visitorsRCY.visibility = View.GONE
            view.addVisitorBTN.hide()
        }
    }

    private fun showWorkingState(visitors: List<Visitor>) {
        view?.let { view ->
            view.loaderPBR.visibility = View.GONE

            view.visitorsRCY.visibility = View.VISIBLE
            view.addVisitorBTN.show()

            (view.visitorsRCY.adapter as VisitorsAdapter).visitors = visitors
        }
    }
}