package com.anenigmatic.secupia.screens.vehicle.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anenigmatic.secupia.R
import com.anenigmatic.secupia.screens.vehicle.core.VehicleLog
import kotlinx.android.synthetic.main.row_vehicle_log.view.*

class VehicleLogsAdapter : RecyclerView.Adapter<VehicleLogsAdapter.VehicleLogVHolder>() {

    var vehicleLogs = listOf<VehicleLog>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = vehicleLogs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleLogVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VehicleLogVHolder(inflater.inflate(R.layout.row_vehicle_log, parent, false))
    }

    override fun onBindViewHolder(holder: VehicleLogVHolder, position: Int) {
        val vehicleLog = vehicleLogs[position]

        holder.dateLBL.text = "${vehicleLog.datetime.month.toString().toLowerCase().capitalize()} ${vehicleLog.datetime.dayOfMonth}, ${vehicleLog.datetime.year}"
        holder.timeLBL.text = vehicleLog.datetime.toLocalTime().toString()

        if(vehicleLog.isEntryLog) {
            holder.actionLBL.text = "Entry"
            holder.actionLBL.setTextColor(ContextCompat.getColor(holder.rootPOV.context, R.color.blu01))
        } else {
            holder.actionLBL.text = "Exit"
            holder.actionLBL.setTextColor(ContextCompat.getColor(holder.rootPOV.context, R.color.red01))
        }
    }


    class VehicleLogVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val dateLBL: TextView = rootPOV.dateLBL
        val timeLBL: TextView = rootPOV.timeLBL
        val actionLBL: TextView = rootPOV.actionLBL
    }
}