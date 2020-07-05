package com.example.admediator

import android.app.Activity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object AdProvider {

    private val threadExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun initialize(activity: Activity, appId: String) {
        threadExecutor.execute {
            AdMediator.initialize(activity, appId)
        }
    }

    fun requestAd(
        activity: Activity,
        mediatorZoneId: String,
        requestAdCallback: RequestAdCallback
    ) {
        threadExecutor.execute {
            AdMediator.requestAd(activity, mediatorZoneId, requestAdCallback)
        }
    }

    fun showAd(activity: Activity, zoneId: String, showAdCallback: ShowAdCallback) {
        threadExecutor.execute {
            AdMediator.showAd(activity, zoneId, showAdCallback)
        }
    }
}