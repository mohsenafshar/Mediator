package com.example.admediator.data

import com.example.admediator.AdMediator
import com.example.admediator.AdNetworkType
import com.example.admediator.AdType
import com.example.admediator.model.AdNetworks
import com.example.admediator.model.AppSetting
import com.example.admediator.model.MediationGroup
import com.example.admediator.model.Waterfall


class LocalRepository: IRepository {

    private val holder: HashMap<String, MediationGroup> = HashMap()

    companion object {
        val TAPSELL_KEY = "bcpejflqnbeqonajhmiancqealhkegckbislfhadbommlpnthaeqmaqipoplpjirtobibd"
        //    const val TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO = "5efea7b789103a0001614dd6"
        val TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO = "5f0035e510484a00016c0b1b"
    }

    override fun getAppSetting(appId: String) : AppSetting {
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

        return MediationGroup(
            zoneType = AdType.Interstitial,
            waterfalls = listOf(
                Waterfall(
                    AdNetworkType.Tapsell,
                    TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO,
                    2000
                )
            ),
            ttl = 360000
        )
    }

    override fun cacheMediationGroup(mediatorZoneId: String, mediationGroup: MediationGroup?) {
        if (holder.get(mediatorZoneId) == null && mediationGroup != null) {
            holder.put(mediatorZoneId, mediationGroup)
        }
    }
}