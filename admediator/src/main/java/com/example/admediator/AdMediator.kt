package com.example.admediator

import android.app.Activity
import android.util.Log
import com.example.admediator.data.LocalRepository.Companion.TAPSELL_ZONE_ID_INTERSTITIAL_VIDEO
import com.example.admediator.di.myKodein
import com.example.admediator.model.Waterfall
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.HashMap

object AdMediator : KodeinAware {

    val waterfallMap: HashMap<String, Waterfall> = HashMap()
    private val mListAdapter: MutableList<AdAdapter> = mutableListOf()
    private val mediationGroupManager: MediationGroupManager by instance()
    private val appSettingManager: AppSettingManager by instance()
    private val isShowingAd = AtomicBoolean(false)

    internal fun initialize(activity: Activity, appId: String) {
        val appSetting = appSettingManager.getAppSetting(appId)

        AdapterInitializer.getAdapters()
        mListAdapter.addAll(AdapterInitializer.hashMapAdapter.values)

        for (adAdapter in mListAdapter) {
            when (adAdapter.getAdNetworkType()) {
                AdNetworkType.Tapsell -> {
                    appSetting.adNetworks.Tapsell?.let {
                        adAdapter.initialize(activity, it)
                    }
                }

                AdNetworkType.Chartboost -> {
                    appSetting.adNetworks.Chartboost?.let {
                        adAdapter.initialize(activity, it)
                    }
                }

                AdNetworkType.UnityAds -> {
                    appSetting.adNetworks.UnityAds?.let {
                        adAdapter.initialize(activity, it)
                    }
                }
            }
        }
    }

    @Synchronized
    internal fun requestAd(activity: Activity, mediatorZoneId: String, requestAdCallback: RequestAdCallback) {
        val mediationGroup = mediationGroupManager.getMediationGroup(mediatorZoneId)
        if (mediationGroup == null) {
            requestAdCallback.onError("Could not find Mediation group")
            return
        }

        AdRequestHandler(activity, mediationGroup, object : InternalAdRequestHandlerCallback {
            override fun onHandled(waterfall: Waterfall) {
                waterfallMap[mediatorZoneId] = waterfall
                requestAdCallback.onAddAvailable(waterfall.adNetwork.name)
            }

            override fun onError(message: String?) {
                requestAdCallback.onError(message)
            }

        }).handle()
    }

    internal fun showAd(activity: Activity, mediatorZoneId: String, showAdCallback: ShowAdCallback) {
        if (isShowingAd.compareAndSet(false, true).not()) {
            Log.d("MEDIATOR", "Ad is showing")
            return
        }
        val waterfall: Waterfall? = getWaterfallForShowableAd(mediatorZoneId)
        AdapterInitializer.hashMapAdapter[waterfall?.adNetwork]?.showAd(activity, waterfall?.zoneId!!, object : ShowAdCallback {
            override fun onOpened() {
                showAdCallback.onOpened()
            }

            override fun onClosed() {
                // when ad showed, remove it from hashmap
                AdapterInitializer.hashMapAdapter.remove(waterfall.adNetwork)
                isShowingAd.set(false)
                showAdCallback.onClosed()
            }

            override fun onRewarded() {
                showAdCallback.onRewarded()
            }

            override fun onError() {
                isShowingAd.set(false)
                showAdCallback.onError()
            }

        })
    }

    private fun getWaterfallForShowableAd(mediatorZoneId: String): Waterfall? {
        return waterfallMap[mediatorZoneId]
    }

    override val kodein: Kodein
        get() = myKodein
}