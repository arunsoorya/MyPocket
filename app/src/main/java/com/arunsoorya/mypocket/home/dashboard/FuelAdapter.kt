package com.arunsoorya.mypocket.home.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.arunsoorya.mypocket.R
import com.arunsoorya.mypocket.sync.PsmsVo
import kotlinx.android.synthetic.main.fuel_display_row.view.*
import kotlinx.android.synthetic.main.sms_list_item.view.*

class FuelAdapter(val context: Context?, val items: List<FuelCity>) : Adapter<FuelAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelAdapter.ViewHolder {

        val inflatedView = LayoutInflater.from(context).inflate(R.layout.fuel_display_row, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FuelAdapter.ViewHolder, position: Int) {
        val fuelCity: FuelCity = items.get(position)
        holder.placeName.text = fuelCity.city + " " + fuelCity.date
        holder.petrol_y.text = "Petrol\n${fuelCity.petrol_yesterday}"
        holder.diesel_y.text = "Diesel\n${fuelCity.diesel_yesterday}"
        holder.petrol_t.text = "Petrol\n${fuelCity.petrol}"
        holder.diesel_t.text = "Diesel\n${fuelCity.diesel}"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var placeName = itemView.placeName
        var petrol_y = itemView.petrol_y
        var diesel_y = itemView.diesel_y
        var petrol_t = itemView.petrol_t
        var diesel_t = itemView.diesel_t
    }
}