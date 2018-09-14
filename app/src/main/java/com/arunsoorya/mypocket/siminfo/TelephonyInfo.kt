package com.arunsoorya.mypocket.siminfo

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.net.Uri
import android.os.Build
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.arunsoorya.mypocket.getValue

class TelephonyInfo {

    var subscribInfos = mutableListOf<SubscriptionInformation>()

    fun checkTelephonyInfo(context: Context): List<SubscriptionInformation> {
        var charSequence: String?
        var subscriptionId: Int
        var charSequence2: String?
        if (Build.VERSION.SDK_INT >= 22) {
            val activeSubscriptionInfoList = SubscriptionManager.from(context).activeSubscriptionInfoList
            if (activeSubscriptionInfoList != null) {
                for (subscriptionInfo in activeSubscriptionInfoList) {
                    val simSlotIndex = subscriptionInfo.simSlotIndex
                    subscriptionId = subscriptionInfo.subscriptionId
                    charSequence = subscriptionInfo.displayName?.toString()
                    charSequence2 = subscriptionInfo.carrierName?.toString()

                    if (charSequence.isNullOrBlank() && !charSequence2.isNullOrBlank()) {
                        charSequence = charSequence2
                    }
                    subscribInfos.add(SubscriptionInformation(charSequence, simSlotIndex, subscriptionId))

                }
            }
        } else {
            val query = context.contentResolver.query(Uri.parse("content://telephony/siminfo/"), null, null, null, null)
            if (query != null) {
                if (query.moveToFirst()) {
                    do {
                        val contentValues = ContentValues()
                        DatabaseUtils.cursorRowToContentValues(query, contentValues)
                        val intValue = contentValues.getValue("sim_id", -1)
                        val intValue2 = contentValues.getValue("slot", intValue)

                        if (intValue2 >= 0) {
//                            if (contentValues.containsKey(FieldType.FOREIGN_ID_FIELD_SUFFIX)) {
//                                intValue = Integer.valueOf(contentValues.get(FieldType.FOREIGN_ID_FIELD_SUFFIX).toString()).toInt()
//                            } else {
//                                intValue = 0
//                            }
                            charSequence = contentValues.getValue("display_name")
                            subscribInfos.add(SubscriptionInformation(charSequence, intValue2, intValue))
                        }
                    } while (query.moveToNext())
                }
                query.close()
            } else {
                charSequence = (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).simOperatorName
                charSequence2 = null
                for (str in arrayOf("getSimOperatorNameGemini",
                        "getDeviceIdDs", "getSimState",
                        "getSimSerialNumberGemini",
                        "getSimOperatorName",
                        "getSimOperatorNameForSubscription")) {
                    try {
                        charSequence = getSimDetails(context, str, 0)
                        charSequence2 = getSimDetails(context, str, 1)
                        if (!(charSequence == null || charSequence2 == null)) {
                            break
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                subscribInfos.add(SubscriptionInformation(charSequence, 0, -1))
                if (charSequence2 != null) {
                    subscribInfos.add(SubscriptionInformation(charSequence, 1, -1))
//                } else {
//                    i.a(b, context)
                }
            }
        }
        return subscribInfos
    }

    private fun getSimDetails(context: Context, str: String, i: Int): String? {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            val invoke = Class.forName(telephonyManager.javaClass.name)
                    .getMethod(str, *arrayOf<Class<*>>(Integer.TYPE))
                    .invoke(telephonyManager, *arrayOf<Any>(Integer.valueOf(i)))
            return invoke?.toString()
        } catch (e: Exception) {
            throw e
        }

    }
}