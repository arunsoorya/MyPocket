package com.arunsoorya.mypocket.data

import androidx.annotation.NonNull
import com.arunsoorya.mypocket.home.dashboard.FuelCity
import com.arunsoorya.mypocket.home.dashboard.FuelPriceResponse
import com.arunsoorya.mypocket.siminfo.SubscriptionInformation
import io.reactivex.Completable

interface DataSource {

    @NonNull
    fun saveSimInformation(@NonNull subscriptionInformation: List<SubscriptionInformation>): Completable


    fun saveFuelInformation(@NonNull fuelDetails: List<FuelCity>): Completable
}
