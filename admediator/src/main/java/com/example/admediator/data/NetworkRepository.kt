package com.example.admediator.data

import com.example.admediator.AdNetworkType
import com.example.admediator.AdType
import com.example.admediator.data.LocalRepository.Companion.TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO
import com.example.admediator.model.AdNetworks
import com.example.admediator.model.AppSetting
import com.example.admediator.model.MediationGroup
import com.example.admediator.model.Waterfall
import java.lang.Exception


class NetworkRepository : IRepository {
    override fun getAppSetting(appId: String): AppSetting {
        // todo : send request to our server and fetch AppSetting
        return AppSetting(
            AdNetworks(
                Chartboost = "ChartboostAppId",
                Tapsell = LocalRepository.TAPSELL_KEY,
                UnityAds = "UnityAdsAppId"
            )
        )
    }

    override fun getMediationGroup(mediatorZoneId: String): MediationGroup? {
        // todo : send request to our server and fetch MediationGroup
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
        throw Exception("This method shouldn't be used")
    }
}