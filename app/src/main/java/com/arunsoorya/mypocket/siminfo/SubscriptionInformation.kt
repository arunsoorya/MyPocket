package com.arunsoorya.mypocket.siminfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SubscriptionInformation(var displayName: String? = null,
                              var simSlotIndex: Int = 0,
                              @PrimaryKey var subscriptionId: Int = 0)