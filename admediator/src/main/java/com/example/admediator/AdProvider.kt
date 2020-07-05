package com.example.admediator

import android.app.Activity
import com.example.admediator.data.IRepository
import com.example.admediator.data.LocalRepository
import com.example.admediator.data.NetworkRepository
import com.example.admediator.data.RepositoryImpl
import com.google.gson.Gson
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module

object AdProvider {

    fun initialize(activity: Activity, appId: String) {
        AdMediator.initialize(activity, appId)
    }

    fun requestAd(
        activity: Activity,
        mediatorZoneId: String,
        requestAdCallback: RequestAdCallback
    ) {
        AdMediator.requestAd(activity, mediatorZoneId, requestAdCallback)
    }

    fun showAd(activity: Activity, zoneId: String, showAdCallback: ShowAdCallback) {
        AdMediator.showAd(activity, zoneId, showAdCallback)
    }
}