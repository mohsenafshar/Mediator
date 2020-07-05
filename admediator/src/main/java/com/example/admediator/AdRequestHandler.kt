package com.example.admediator

import android.app.Activity
import com.example.admediator.model.MediationGroup
import com.example.admediator.model.Waterfall
import com.example.admediator.model.Zone
import java.util.*

class AdRequestHandler(private val activity: Activity, private val mediationGroup: MediationGroup, private val internalAdRequestHandlerCallback: InternalAdRequestHandlerCallback) {
    private val waterfallQueue: LinkedList<Waterfall> = LinkedList()
    private lateinit var latestWaterfall: Waterfall

    init {
        waterfallQueue.addAll(mediationGroup.waterfalls)
    }

    private val adRequestHandlerCallback = object : AdRequestHandlerCallback {
        override fun onHandled(adNetworkType: AdNetworkType) {
            if (::latestWaterfall.isInitialized)
                internalAdRequestHandlerCallback.onHandled(latestWaterfall)
            else {
                internalAdRequestHandlerCallback.onError("Something went wrong")
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

        when (mediationGroup.zoneType) {
            AdType.Interstitial -> adAdapter.requestInterstitialAd(activity, zone.waterfall.zoneId, adRequestHandlerCallback)
            AdType.Rewarded -> adAdapter.requestRewardedAd(activity, zone.waterfall.zoneId, adRequestHandlerCallback)
        }
    }
}