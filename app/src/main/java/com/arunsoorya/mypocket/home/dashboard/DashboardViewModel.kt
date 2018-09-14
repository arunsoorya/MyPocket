package com.arunsoorya.mypocket.home.dashboard

import android.app.Activity
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.net.Network
import android.net.Uri
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import com.arunsoorya.mypocket.BuildConfig
import com.arunsoorya.mypocket.getDatabase
import com.arunsoorya.mypocket.logd
import com.arunsoorya.mypocket.services.FuelPriceRetrofitInterface
import com.arunsoorya.mypocket.siminfo.SubscriptionInformation
import com.arunsoorya.mypocket.siminfo.TelephonyInfo
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException


class DashboardViewModel(activity: Activity) : AndroidViewModel(activity.application) {

    private val context: Context = activity.applicationContext
    var mLoadingIndicatorSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    var mSnackbarText: PublishSubject<String> = PublishSubject.create()

    fun downloadInformation(): Single<Boolean> {

        val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(chain.request()
                            .newBuilder()
                            .addHeader("X-Mashape-Key", "NDUCSqq2HJmsh6XypHmL1ItgjSqLp1tbp4zjsnCxf4FozfoWqV")
                            .addHeader("Accept", "application/json")
                            .build())
                }
                .build()
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://newsrain-petrol-diesel-prices-india-v1.p.mashape.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val fuelPriceRetrofitInterface: FuelPriceRetrofitInterface = retrofit.create(FuelPriceRetrofitInterface::class.java)
        return fuelPriceRetrofitInterface.getAll()
                .doOnSubscribe { mLoadingIndicatorSubject.onNext(true) }
                .doOnError {
                    mSnackbarText.onError(Throwable("Un able to fetch Sim information"))
                    mLoadingIndicatorSubject.onNext(false)
                }
                .doOnSuccess {
                    mLoadingIndicatorSubject.onNext(false)
                    mSnackbarText.onNext("Sim Information")
                }
                .map { fuelPrice: FuelPriceResponse ->
                    context.getDatabase()!!.fuelInfoDAO().insertAll(*fuelPrice.cities.toTypedArray())
                    true
                }
    }

    fun getFuelList(): Flowable<List<FuelCity>> {
        return context.getDatabase()!!.fuelInfoDAO().getAll();
    }

}