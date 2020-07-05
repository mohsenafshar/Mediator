package com.example.admediator

import android.app.Activity

interface AdAdapter {
    fun initialize(activity: Activity, appId: String)
    fun requestRewardedAd(activity: Activity, zoneId: String, adRequestHandlerCallback: AdRequestHandlerCallback)
    fun requestInterstitialAd(
        activity: Activity,
        zoneId: String,
        adRequestHandlerCallback: AdRequestHandlerCallback
    )

    fun showAd(activity: Activity, zoneId: String, showAdCallback: ShowAdCallback)
    fun getAdNetworkType(): AdNetworkType
}