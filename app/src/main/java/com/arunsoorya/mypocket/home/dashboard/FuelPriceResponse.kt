package com.arunsoorya.mypocket.home.dashboard

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FuelPriceResponse(
        val cities: List<FuelCity>
)
@Entity(tableName = "FuelCity")
data class FuelCity(
        val city: String,
        val petrol: String,
        val diesel: String,
        val petrol_yesterday: String,
        val diesel_yesterday: String,
        val state: String,
        val date: String,
        @PrimaryKey(autoGenerate = true) val fuelId:Int
)