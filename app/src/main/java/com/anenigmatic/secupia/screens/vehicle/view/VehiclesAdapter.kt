package com.anenigmatic.secupia.screens.vehicle.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.vehicle.core.Vehicle
import kotlinx.android.synthetic.main.row_vehicle.view.*

class VehiclesAdapter(private val listener: ClickListener) : RecyclerView.Adapter<VehiclesAdapter.VehicleVHolder>() {

    interface ClickListener {

        fun onVehicleClicked(id: Long)
    }


    var vehicles = listOf<Vehicle>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = vehicles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VehicleVHolder(inflater.inflate(R.layout.row_vehicle, parent, false))
    }

    override fun onBindViewHolder(holder: VehicleVHolder, position: Int) {
        val vehicle = vehicles[position]

        holder.nameLBL.text = vehicle.name
        holder.registrationNoLBL.text = vehicle.registrationNo

        if(vehicle.isInside) {
            holder.statusLBL.text = "Parked"
            holder.statusLBL.setTextColor(ContextCompat.getColor(holder.rootPOV.context, R.color.blu01))
        } else {
            holder.statusLBL.text = "Out"
            holder.statusLBL.setTextColor(ContextCompat.getColor(holder.rootPOV.context, R.color.red01))
        }

        holder.rootPOV.setOnClickListener {
            listener.onVehicleClicked(vehicle.id)
        }
    }


    class VehicleVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val registrationNoLBL: TextView = rootPOV.registrationNoLBL
        val statusLBL: TextView = rootPOV.statusLBL
    }
}