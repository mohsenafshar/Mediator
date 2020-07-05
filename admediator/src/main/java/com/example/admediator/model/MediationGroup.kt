package com.example.admediator.model

import com.example.admediator.AdNetworkType
import com.example.admediator.AdType
import java.util.*
import kotlin.properties.Delegates

class MediationGroup (
    val zoneType: AdType,
    val waterfalls : List<Waterfall>,
    val ttl: Long
) {
    var startTime by Delegates.notNull<Long>()

    init {
        startTime = Calendar.getInstance().timeInMillis
    }

    fun isValid(): Boolean {
        return startTime + ttl > Calendar.getInstance().timeInMillis
    }
}

data class Waterfall (
    val adNetwork: AdNetworkType,
    val zoneId: String,
    val timeout: Long
) {

}