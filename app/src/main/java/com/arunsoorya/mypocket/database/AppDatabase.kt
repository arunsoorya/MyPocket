package com.arunsoorya.mypocket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arunsoorya.mypocket.home.dashboard.FuelCity
import com.arunsoorya.mypocket.siminfo.SubscriptionInformation

@Database(entities = [SubscriptionInformation::class, FuelCity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subscriptionInfoDao(): SubscriptionInformationDAO
    abstract fun fuelInfoDAO(): FuelInfoDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "myPocket.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}


