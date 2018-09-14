package com.arunsoorya.mypocket

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.arunsoorya.mypocket.database.AppDatabase
import androidx.room.Room


fun ContentValues?.getValue(key: String, default: Int): Int {
    if (this != null && this.containsKey("sim_id"))
        return this.get("sim_id").toString().toInt()

    return -1
}

fun ContentValues?.getValue(key: String, default: String = ""): String {
    if (this != null && this.containsKey("sim_id"))
        return this.get("sim_id").toString()
    return default
}

fun Context.isPermissionGranted(vararg permissions: String): Boolean {
    var permissionGranted = false
    for (permission: String in permissions) {
        permissionGranted =  (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED)
    }
    return permissionGranted
}

fun Context.getDatabase(): AppDatabase? {
    return AppDatabase.getInstance(applicationContext)

}
fun Any.logd() {
//    this::class.java.simpleName
    if (BuildConfig.DEBUG) Log.d("", this.toString())
    if (BuildConfig.DEBUG) Log.d(this.javaClass.name, this.toString())
}

fun Activity.logd(message: String) {
//    this::class.java.simpleName
    if (BuildConfig.DEBUG) Log.d("", message)
    if (BuildConfig.DEBUG) Log.d(this.javaClass.name, message)
}

fun LayoutInflater.inflateView(@LayoutRes layoutId: Int, container: ViewGroup?): View {
    return this.inflate(layoutId, container, false)
}

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

