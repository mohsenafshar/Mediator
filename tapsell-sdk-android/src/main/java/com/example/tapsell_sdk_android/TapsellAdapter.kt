package com.example.tapsell_sdk_android

import android.app.Activity
import android.util.Log
import com.example.admediator.AdAdapter
import com.example.admediator.AdMediator
import com.example.admediator.RequestAdCallback
import com.example.admediator.ShowAdCallback
import ir.tapsell.sdk.*
import ir.tapsell.sdk.TapsellAdActivity.AD_ID
import ir.tapsell.sdk.TapsellAdActivity.ZONE_ID
import java.util.concurrent.atomic.AtomicBoolean


class TapsellAdapter : AdAdapter {

    val isInitialized: AtomicBoolean = AtomicBoolean(false)
//    lateinit var activity: Activity

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
            AdMediator.register(get())
        }
    }

    private fun requestTapsellInterstitialAd(
        activity: Activity,
        zoneId: String,
        requestAdCallback: RequestAdCallback
    ) {
        Tapsell.requestAd(activity,
            ZONE_ID,
            TapsellAdRequestOptions(),
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    requestAdCallback.onAddAvailable(adId)
                }

                override fun onError(message: String) {
                    requestAdCallback.onError(message)
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
                    showAdCallback.onError()
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
        requestAdCallback: RequestAdCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun requestInterstitialAd(
        activity: Activity,
        zoneId: String,
        requestAdCallback: RequestAdCallback
    ) {
        requestTapsellInterstitialAd(activity, zoneId, requestAdCallback)
    }

    override fun showAd(activity: Activity, zoneId: String,adId: String, showAdCallback: ShowAdCallback) {
        showTapsellAd(activity, zoneId, adId, showAdCallback)
    }

    private fun log(message: String?) {
        Log.d("TAPSEL_LOG", message.toString())
    }

}