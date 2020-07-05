package com.example.admediator

import com.example.admediator.model.Waterfall

interface InternalAdRequestHandlerCallback {
    fun onHandled(waterfall: Waterfall)
    fun onError(message: String?)
}

interface AdRequestHandlerCallback {
    fun onHandled(adNetworkType: AdNetworkType)
    fun onError(message: String?)
}

interface RequestAdCallback {
    fun onAddAvailable(adNetworkName: String)
    fun onError(message: String?)
}

interface ShowAdCallback {
    fun onOpened()
    fun onClosed()
    fun onRewarded()
    fun onError()
}

class InnerCallback {
    var onHandled: ((adNetworkType: AdNetworkType) -> Unit)? = null
    var onError: ((message: String?) -> Unit)? = null
}