package com.arunsoorya.mypocket.sync

import android.app.Activity
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.net.Uri
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import com.arunsoorya.mypocket.getDatabase
import com.arunsoorya.mypocket.logd
import com.arunsoorya.mypocket.siminfo.SubscriptionInformation
import com.arunsoorya.mypocket.siminfo.TelephonyInfo
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.ArrayList

class SyncViewModel(activity: Activity) : AndroidViewModel(activity.application) {
    private val SMS_FIELDS = arrayOf("address", "body", "date", "read", "sub_id", "sim_id", "sim_slot", "thread_id")
//    private val n = arrayOf("address", "body", "date", FieldType.FOREIGN_ID_FIELD_SUFFIX, "read", "sub_id", "sim_id", "sim_slot", "thread_id", PersistedEntity.EntityType)

    private val context: Context = activity.applicationContext
    var mLoadingIndicatorSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    var mSnackbarText: PublishSubject<String> = PublishSubject.create()

    fun getSimInformation(): Observable<List<SubscriptionInformation>> {

        val subscriptionInformation: List<SubscriptionInformation> = TelephonyInfo().checkTelephonyInfo(context)


//            sim1Details.set(subscriptionInformation?.get(0))
//            sim2Details.set(subscriptionInformation?.get(1))

//        context.getDatabase()!!.subscriptionInfoDao().insertAll(*subscriptionInformation?.toTypedArray()!!)
        syncAllContacts(context)
        return Observable.fromArray(subscriptionInformation)
                .doOnSubscribe { mLoadingIndicatorSubject.onNext(true) }
                .doOnNext { mSnackbarText.onNext("fetching sim information...") }
                .doOnError { mSnackbarText.onError(Throwable("Un able to fetch Sim information")) }
                .doOnComplete {
                    mLoadingIndicatorSubject.onNext(false)
                    mSnackbarText.onNext("Sim Information")
                }
    }

    fun syncAllContacts(context: Context) {
        val query: Cursor? = context.contentResolver.query(Uri.parse("content://sms"), null, null, null, "date DESC LIMIT 500")
        try {
            val arrayList = mutableListOf<String>()
            val contentValues = ContentValues()
            DatabaseUtils.cursorRowToContentValues(query, contentValues)
            for (str in SMS_FIELDS) {
                if (contentValues.containsKey(str)) {
                    arrayList.add(str)
                }
            }

            arrayList.logd()

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            query?.close()
        }
    }


}