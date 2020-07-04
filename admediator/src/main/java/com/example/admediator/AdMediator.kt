package com.example.admediator

import android.app.Activity
import android.util.Log
import com.example.admediator.model.MediationGroup
import com.example.admediator.model.Waterfall
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance

object AdMediator {
    const val TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO = "5efea7b789103a0001614dd6"

    private val listAdapter = mutableListOf<AdAdapter>()

    fun register(adAdapter: AdAdapter) {
        listAdapter.add(adAdapter)
    }

    private fun getAdapters() {
        val list = listOf("com.example.tapsell_sdk_android.TapsellAdapter")
        for (className in list) {
            getAdapter(className)
        }
    }

    private fun getAdapter(className: String) {
        val kclass: KClass<*> = Class.forName(className).kotlin
        val companionKclass = kclass.companionObject
        val companionInstance = kclass.companionObjectInstance
        val getMethod = companionKclass?.java?.getMethod("register")
        getMethod?.invoke(companionInstance)
    }

    internal fun initialize(activity: Activity, appId: String) {
        getAdapters()

        for (adAdapter in listAdapter) {
            adAdapter.initialize(activity, appId)
        }
    }

    private fun getMediationGroup(zoneId: String): MediationGroup {
        return MediationGroup(
            zoneType = AdType.Interstitial.name,
            waterfalls = listOf(
                Waterfall(
                    AdNetworkType.Tapsell.name,
                    TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO,
                    2000
                )
            ),
            ttl = 360000
        )
    }

    internal fun requestAd(activity: Activity, zoneId: String, requestAdCallback: RequestAdCallback) {
        // get waterfall
        val mediationGrouping = getMediationGroup(zoneId)
//        for (waterfall in mediationGrouping.waterfalls) {
//            when(waterfall.adNetwork) {
//                AdNetworkType.Tapsell.name -> {
//
//                }
//            }
//        }

        when(mediationGrouping.zoneType) {
            AdType.Interstitial.name -> {
                for ((index, adAdapter) in listAdapter.withIndex()) {
                    synchronized(AdMediator) {
                        adAdapter.requestInterstitialAd(activity, mediationGrouping.waterfalls[index].zoneId, requestAdCallback)
                    }
                }
            }

            AdType.Rewarded.name -> {
                for ((index, adAdapter) in listAdapter.withIndex()) {
                    synchronized(AdMediator) {
                        adAdapter.requestRewardedAd(activity, mediationGrouping.waterfalls[index].zoneId, requestAdCallback)
                    }
                }
            }
        }
    }

    private fun requestRewardedAd(activity: Activity, zoneId: String, requestAdCallback: RequestAdCallback) {

    }

    private fun requestInterstitialAd(activity: Activity, zoneId: String, requestAdCallback: RequestAdCallback) {

    }

    internal fun showAd(activity: Activity, zoneId: String,adId: String, showAdCallback: ShowAdCallback) {
        for (adAdapter in listAdapter) {
            adAdapter.showAd(activity, zoneId, adId, showAdCallback)
        }
    }

    private fun getNextAdAdapter() {
        return
    }

    private fun log(message: String?) {
        Log.d("TAPSEL_LOG", message.toString())
    }
}