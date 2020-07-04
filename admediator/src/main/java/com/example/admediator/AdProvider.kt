package com.example.admediator

import android.app.Activity
import com.google.gson.Gson

object AdProvider {

    const val TAPSELL_KEY = "bcpejflqnbeqonajhmiancqealhkegckbislfhadbommlpnthaeqmaqipoplpjirtobibd"


    fun initialize(activity: Activity, appId : String) {

        // get app setting based on appId
//        val appSetting = getAppSetting(appId)

        // initialize adapters
        AdMediator.initialize(activity, TAPSELL_KEY)
    }

    private fun getAppSetting(appId: String): java.util.HashMap<*, *>? {
        val json = "\"Tapsell\": \"${TAPSELL_KEY}\", \"UnityAds\": \"appIdInUnityAds\", \"Chartboost\": \"appIdInChartboost\""
        return Gson().fromJson(json, HashMap::class.java)
    }

    fun requestAd(activity: Activity, mediatorZoneId: String, requestAdCallback: RequestAdCallback) {
        AdMediator.requestAd(activity, mediatorZoneId, requestAdCallback)
    }

    fun showAd(activity: Activity, zoneId: String,adId: String,  showAdCallback: ShowAdCallback) {
        AdMediator.showAd(activity, zoneId, adId, showAdCallback)
    }
}