package com.arunsoorya.mypocket.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.arunsoorya.mypocket.siminfo.SubscriptionInformation
import io.reactivex.Completable

@Dao
interface SubscriptionInformationDAO {

    @Query("SELECT * FROM SubscriptionInformation")
    fun getAll(): List<SubscriptionInformation>

    @Query("SELECT * FROM SubscriptionInformation WHERE subscriptionId IN (:subscriptionId)")
    fun loadAllByIds(subscriptionId: Int): SubscriptionInformation

    @Insert
    fun  insertAll(vararg subscriptionInformations: SubscriptionInformation)

    @Delete
    fun delete(subscriptionInformation: SubscriptionInformation)
}