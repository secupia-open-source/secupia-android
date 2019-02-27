package com.anenigmatic.secupia.screens.visitors.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.visitors.core.Visitor
import kotlinx.android.synthetic.main.row_visitor.view.*

class VisitorsAdapter(private val listener: ClickListener) : RecyclerView.Adapter<VisitorsAdapter.VisitorVHolder>() {

    interface ClickListener {

        fun onEditVisitorAction(id: Long)
    }


    var visitors = listOf<Visitor>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = visitors.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitorVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VisitorVHolder(inflater.inflate(R.layout.row_visitor, parent, false))
    }

    override fun onBindViewHolder(holder: VisitorVHolder, position: Int) {
        val visitor = visitors[position]

        holder.nameLBL.text = visitor.name
        holder.dateLBL.text = "${visitor.datetime.month.toString().toLowerCase().capitalize()} ${visitor.datetime.dayOfMonth}, ${visitor.datetime.year}"
        holder.timeLBL.text = visitor.datetime.toLocalTime().toString().take(5)

        holder.editBTN.setOnClickListener {
            listener.onEditVisitorAction(visitor.id)
        }
    }
    

    class VisitorVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val dateLBL: TextView = rootPOV.dateLBL
        val timeLBL: TextView = rootPOV.timeLBL
        val editBTN: ImageView = rootPOV.editBTN
    }
}