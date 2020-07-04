package com.example.admediator

import android.app.Activity

interface AdAdapter {
    fun initialize(activity: Activity, appId: String)
    fun requestRewardedAd(activity: Activity, zoneId: String, requestAdCallback: RequestAdCallback)
    fun requestInterstitialAd(
        activity: Activity,
        zoneId: String,
        requestAdCallback: RequestAdCallback
    )

    fun showAd(activity: Activity, zoneId: String, adId: String, showAdCallback: ShowAdCallback)

}

interface RequestAdCallback {
    fun onAddAvailable(adId: String)
    fun onError(message: String?)
}

interface ShowAdCallback {
    fun onOpened()
    fun onClosed()
    fun onRewarded()
    fun onError()
}