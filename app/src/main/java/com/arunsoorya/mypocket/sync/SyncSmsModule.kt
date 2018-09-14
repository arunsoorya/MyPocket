package com.arunsoorya.mypocket.sync

import android.app.Activity
import android.app.Application
import android.content.Context

class SyncSmsModule{

    companion object {
       fun createSyncViewModel(activity: Activity) : SyncViewModel{
            return SyncViewModel(activity)
        }
    }
}