package com.example.admediator

import android.util.Log
import java.lang.Exception
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance

object AdapterInitializer {
    private const val TAG = "AdapterInitializer"
    val hashMapAdapter = hashMapOf<AdNetworkType, AdAdapter>()

    fun register(adAdapter: AdAdapter) {
        hashMapAdapter[adAdapter.getAdNetworkType()] = adAdapter
    }

    fun getAdapters() {
        val list = listOf("com.example.tapsell_sdk_android.TapsellAdapter", "com.example.tapsell_sdk_android.ChartboostAdapter", "com.example.tapsell_sdk_android.UnityAdsAdapter")
        for (className in list) {
            getAdapter(className)
        }
    }

    private fun getAdapter(className: String) {
        try {
            val kclass: KClass<*> = Class.forName(className).kotlin
            val companionKclass = kclass.companionObject
            val companionInstance = kclass.companionObjectInstance
            val getMethod = companionKclass?.java?.getMethod("register")
            getMethod?.invoke(companionInstance)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }
}