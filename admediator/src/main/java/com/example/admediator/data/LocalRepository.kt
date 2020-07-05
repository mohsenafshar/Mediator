package com.example.admediator.data

import com.example.admediator.model.AdNetworks
import com.example.admediator.model.AppSetting
import com.example.admediator.model.MediationGroup


class LocalRepository : IRepository {

    private val holder: HashMap<String, MediationGroup> = HashMap()

    companion object {
        val TAPSELL_KEY = "bcpejflqnbeqonajhmiancqealhkegckbislfhadbommlpnthaeqmaqipoplpjirtobibd"
    }

    override fun getAppSetting(appId: String): AppSetting {
        return AppSetting(
            AdNetworks(
                Chartboost = "ChartboostAppId",
                Tapsell = TAPSELL_KEY,
                UnityAds = "UnityAdsAppId"
            )
        )
    }

    override fun getMediationGroup(mediatorZoneId: String): MediationGroup? {
        return holder.get(mediatorZoneId)
    }

    override fun cacheMediationGroup(mediatorZoneId: String, mediationGroup: MediationGroup?) {
        if (holder.get(mediatorZoneId) == null && mediationGroup != null) {
            holder.put(mediatorZoneId, mediationGroup)
        }
    }
}