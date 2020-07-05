package com.example.tapsell_sdk_android

import android.app.Activity
import android.util.Log
import com.example.admediator.*
import ir.tapsell.sdk.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean


class TapsellAdapter private constructor(): AdAdapter {

    val isInitialized: AtomicBoolean = AtomicBoolean(false)
    private val adsMap = HashMap<String, String>()

    companion object {
        private var adapter: TapsellAdapter? = null

        @Synchronized
        fun get(): TapsellAdapter {
            if (adapter == null) {
                adapter = TapsellAdapter()
            }
            return adapter!!
        }

        fun register() {
            AdapterInitializer.register(get())
        }
    }

    private fun requestTapsellInterstitialAd(
        activity: Activity,
        zoneId: String,
        adRequestHandlerCallback: AdRequestHandlerCallback
    ) {
        Tapsell.requestAd(activity,
            zoneId,
            TapsellAdRequestOptions(),
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    adsMap.put(zoneId, adId)
                    Executors.newSingleThreadExecutor().submit {
                        Thread.sleep(1500)
                        adRequestHandlerCallback.onHandled(getAdNetworkType(), zoneId)
                    }
                }

                override fun onError(message: String) {
                    adRequestHandlerCallback.onError(message)
                    log(message)
                }
            })
    }

    private fun showTapsellAd(
        activity: Activity,
        zoneId: String,
        adId: String,
        showAdCallback: ShowAdCallback
    ) {
        Tapsell.showAd(activity,
            zoneId,
            adId,
            TapsellShowOptions(),
            object : TapsellAdShowListener() {
                override fun onOpened() {
                    showAdCallback.onOpened()
                    //ad opened
                    log("Opened")
                }

                override fun onClosed() {
                    showAdCallback.onClosed()
                    //ad closed
                    log("closed")
                }

                override fun onError(message: String) {
                    showAdCallback.onError(message)
                    //error
                    log("error")
                }

                override fun onRewarded(completed: Boolean) {
                    showAdCallback.onRewarded()
                    //reward
                    log("reward")
                }
            })
    }

    override fun initialize(activity: Activity, appId: String) {
        Tapsell.initialize(activity, appId); }

    override fun requestRewardedAd(
        activity: Activity,
        zoneId: String,
        adRequestHandlerCallback: AdRequestHandlerCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun requestInterstitialAd(
        activity: Activity,
        zoneId: String,
        adRequestHandlerCallback: AdRequestHandlerCallback
    ) {
        requestTapsellInterstitialAd(activity, zoneId, adRequestHandlerCallback)
    }

    override fun showAd(activity: Activity, zoneId: String, showAdCallback: ShowAdCallback) {
        val adId = adsMap[zoneId]
        if (adId == null) {
            showAdCallback.onError("Couldn't find ad")
            return
        }
        showTapsellAd(activity, zoneId, adId, showAdCallback)
    }

    override fun getAdNetworkType(): AdNetworkType {
        return AdNetworkType.Tapsell
    }

    private fun log(message: String?) {
        Log.d("TAPSEL_LOG", message.toString())
    }

}