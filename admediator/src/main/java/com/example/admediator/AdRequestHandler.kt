package com.example.admediator

import android.app.Activity
import android.util.Log
import com.example.admediator.model.MediationGroup
import com.example.admediator.model.Waterfall
import com.example.admediator.model.Zone
import java.util.*
import kotlin.collections.HashMap


class AdRequestHandler(private val activity: Activity, private val mediationGroup: MediationGroup, private val internalAdRequestHandlerCallback: InternalAdRequestHandlerCallback) {
    private val waterfallQueue: LinkedList<Waterfall> = LinkedList()
    private lateinit var latestWaterfall: Waterfall
    private val map = HashMap<AdNetworkType, RequestState>()

    init {
        waterfallQueue.addAll(mediationGroup.waterfalls)
    }

    private val adRequestHandlerCallback = object : AdRequestHandlerCallback {
        override fun onHandled(adNetworkType: AdNetworkType, zoneId: String) {
            if (map[adNetworkType] == RequestState.Started) {
                // is succeed
                internalAdRequestHandlerCallback.onHandled(latestWaterfall)
                Log.d("AdRequestHandler", "zoneId: $zoneId")
            } else {
                internalAdRequestHandlerCallback.onError("Request From $adNetworkType with zoneId: $zoneId timeout")
                handle()
            }
        }

        override fun onError(message: String?) {
            internalAdRequestHandlerCallback.onError(message)
            handle()
        }

    }

    private fun getNextZone(): Zone? {
        val waterfall = waterfallQueue.poll()
        if (waterfall == null) {
            return null
        }

        latestWaterfall = waterfall

        return Zone(
            mediationGroup.zoneType,
            waterfall
        )
    }

    tailrec fun handle() {
        val zone = getNextZone()

        if (zone == null) {
            if (waterfallQueue.isEmpty()) {
                internalAdRequestHandlerCallback.onError("Ad Not found")
                return
            }

            handle()
            return
        }

        val adAdapter = AdapterInitializer.hashMapAdapter[zone.waterfall.adNetwork]
        if (adAdapter == null) {
            handle()
            return
        }

        map[zone.waterfall.adNetwork] = RequestState.Started
        delay(zone.waterfall.timeout) {
            map[zone.waterfall.adNetwork] = RequestState.Timeout
        }

        when (mediationGroup.zoneType) {
            AdType.Interstitial -> adAdapter.requestInterstitialAd(activity, zone.waterfall.zoneId, adRequestHandlerCallback)
            AdType.Rewarded -> adAdapter.requestRewardedAd(activity, zone.waterfall.zoneId, adRequestHandlerCallback)
        }
    }

    private fun delay(delay: Long, block: () -> Unit) {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                block()
            }
        }
        val timer = Timer()
        timer.schedule(task, delay)
    }
}
