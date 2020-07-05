package com.example.admediator

import android.app.Activity
import android.util.Log
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
    private val isAppInitialized = AtomicBoolean(false)

    @Synchronized
    internal fun initialize(activity: Activity, appId: String) {
        if (isAppInitialized.get()) return

        val appSetting = appSettingManager.getAppSetting(appId)
        if (appSetting == null) return

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
        isAppInitialized.set(true)
    }

    internal fun requestAd(activity: Activity, mediatorZoneId: String, requestAdCallback: RequestAdCallback) {
        if (isAppInitialized.get().not()) {
            requestAdCallback.onError("Mediator is not initialized")
        }
//        Thread.sleep(10000)

        val mediationGroup = mediationGroupManager.getMediationGroup(mediatorZoneId)
        if (mediationGroup == null) {
            requestAdCallback.onError("Could not find Mediation group")
            return
        }

        AdRequestHandler(activity, mediationGroup, object : InternalAdRequestHandlerCallback {
            override fun onHandled(waterfall: Waterfall) {
                waterfallMap[mediatorZoneId] = waterfall
                requestAdCallback.onAddAvailable(waterfall.adNetwork.name + " " + mediatorZoneId)
            }

            override fun onError(message: String?) {
                requestAdCallback.onError(message)
            }

        }).handle()
    }

    internal fun showAd(activity: Activity, mediatorZoneId: String, showAdCallback: ShowAdCallback) {
        val waterfall: Waterfall? = getWaterfallForShowableAd(mediatorZoneId)
        if (waterfall == null) {
            Log.d("MEDIATOR", "There is not any provided ad for this zone id: $mediatorZoneId")
            showAdCallback.onError("There is not any provided ad for this zone id: $mediatorZoneId")
            return
        }

        if (isShowingAd.compareAndSet(false, true).not()) {
            Log.d("MEDIATOR", "Ad is showing, id: $mediatorZoneId")
            showAdCallback.onError("Ad is showing, id: $mediatorZoneId")
            return
        }

        AdapterInitializer.hashMapAdapter[waterfall.adNetwork]?.showAd(activity, waterfall.zoneId, object : ShowAdCallback {
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

            override fun onError(message: String?) {
                isShowingAd.set(false)
                showAdCallback.onError(message)
            }

        })
    }

    private fun getWaterfallForShowableAd(mediatorZoneId: String): Waterfall? {
        return waterfallMap[mediatorZoneId]
    }

    override val kodein: Kodein
        get() = myKodein
}