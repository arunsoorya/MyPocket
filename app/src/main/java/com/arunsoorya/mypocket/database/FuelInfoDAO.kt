package com.arunsoorya.mypocket.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.arunsoorya.mypocket.home.dashboard.FuelCity
import com.arunsoorya.mypocket.siminfo.SubscriptionInformation
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface FuelInfoDAO {

    @Query("SELECT * FROM FuelCity")
    fun getAll(): Flowable<List<FuelCity>>

    @Query("SELECT * FROM FuelCity WHERE fuelId IN (:fuelId)")
    fun loadAllByIds(fuelId: Int): FuelCity

    @Insert
    fun  insertAll(vararg fuelCity: FuelCity)

    @Delete
    fun delete(fuelCity: FuelCity)
}