package com.arunsoorya.mypocket.sync

import android.Manifest
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.content.pm.PackageManager
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.arunsoorya.mypocket.R
import com.arunsoorya.mypocket.isPermissionGranted
import com.arunsoorya.mypocket.siminfo.SubscriptionInformation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sync_sms_new.*

class SyncSmsActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS: Int = 1011
    lateinit var syncModel: SyncViewModel
    private var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_sms_new)
        syncModel = SyncSmsModule.createSyncViewModel(this)

//        syncModel = ViewModelProviders.of(this).get(SyncViewModel::class.java)
        setSupportActionBar(toolbar)

        checkPermission()

    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    override fun onPause() {
        unbindViewModel()
        super.onPause()
    }

    private fun unbindViewModel() {
        compositeDisposable.clear()
    }

    fun readAllContacts() {
        syncModel.getSimInformation()

    }

    fun bindViewModel() {
        val disposable1: Disposable = syncModel.mLoadingIndicatorSubject
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it)
                        progressbar.visibility = View.VISIBLE
                    else
                        progressbar.visibility = View.INVISIBLE
                }, {
                    it.printStackTrace()
                })

        val disposable2: Disposable = syncModel.mSnackbarText
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ showSyncMessage(it) },
                        { it.printStackTrace() })

        val simInfo: Disposable = syncModel.getSimInformation()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ showSimInfo(it) },
                        { it.printStackTrace() })


        compositeDisposable.add(disposable1)
        compositeDisposable.add(disposable2)
        compositeDisposable.add(simInfo)
    }

    fun showSimInfo(simInfos: List<SubscriptionInformation>) {
        primarySim.text = getSimInfoFormatted("Primary\n", simInfos.getOrNull(0)?.displayName)
        secondarySim.text = getSimInfoFormatted("Secondary\n", simInfos.getOrNull(1)?.displayName)
    }

    fun getSimInfoFormatted(header: String, subHeader: String?): SpannableString {

        val completeStr: String = "$header $subHeader"
        val spannableString: SpannableString = SpannableString(completeStr)
        spannableString.setSpan(RelativeSizeSpan(0.7f), header.length, completeStr.length, 0)
        return spannableString
    }

    fun showSyncMessage(message: String) {
        syncMessage.text = message
    }

    fun checkPermission() {

        if (!isPermissionGranted(Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE)) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            readAllContacts()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    readAllContacts()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
