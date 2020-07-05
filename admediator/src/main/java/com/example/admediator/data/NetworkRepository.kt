package com.example.admediator.data

import com.example.admediator.AdNetworkType
import com.example.admediator.AdType
import com.example.admediator.model.AdNetworks
import com.example.admediator.model.AppSetting
import com.example.admediator.model.MediationGroup
import com.example.admediator.model.Waterfall
import java.lang.Exception


class NetworkRepository : IRepository {

    companion object {
        const val TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO_1 = "5f0035e510484a00016c0b1b"
        const val TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO_2 = "5efea7b789103a0001614dd6"
    }


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

        val zoneId = if (mediatorZoneId.contains('2')) TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO_2 else TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO_1

        return MediationGroup(
            zoneType = AdType.Interstitial,
            waterfalls = listOf(
                Waterfall(
                    AdNetworkType.Tapsell,
                    zoneId,
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