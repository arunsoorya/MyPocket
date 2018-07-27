package com.arunsoorya.mypocket.sync

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arunsoorya.mypocket.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sync_sms.*


class SyncSmsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_sms)
        setSupportActionBar(toolbar)



    }

}
