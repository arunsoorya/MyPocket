package com.arunsoorya.mypocket.data

import android.content.Context
import com.arunsoorya.mypocket.getDatabase
import com.arunsoorya.mypocket.siminfo.SubscriptionInformation
import io.reactivex.Completable

//class  PocketRepository(private val context: Context): DataSource{
//
//    override fun saveFuelInformation(fuelDetails: List<City>): Completable {
//        context.getDatabase()!!.subscriptionInfoDao().insertAll(*subscriptionInformation?.toTypedArray()!!)
//    }
//
//    override fun saveSimInformation(subscriptionInformation: List<SubscriptionInformation>?): Completable {
//        context.getDatabase()!!.subscriptionInfoDao().insertAll(*subscriptionInformation?.toTypedArray()!!)
//
//    }
//
//}