package com.arunsoorya.mypocket.services

import com.arunsoorya.mypocket.home.dashboard.FuelPriceResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface FuelPriceRetrofitInterface {

    @GET("capitals")
    fun getAll(): Single<FuelPriceResponse>
}