package com.example.testtapsell

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.admediator.*
import com.example.tapsell_sdk_android.AdapterTest


class MainActivity : AppCompatActivity() {

    companion object {
        const val MEDIATOR_APP_ID = "MEDIATOR_APP_ID"
        const val MEDIATOR_ZONE_ID_1 = "MEDIATOR_ZONE_ID_1"
        const val MEDIATOR_ZONE_ID_2 = "MEDIATOR_ZONE_ID_2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AdProvider.initialize(this@MainActivity, MEDIATOR_APP_ID)
        Handler().postDelayed({
            requestAd(MEDIATOR_ZONE_ID_1)
            requestAd(MEDIATOR_ZONE_ID_2)
        }, 2000)
    }

    private fun requestAd(mediatorZoneId: String) {
        AdProvider.requestAd(this@MainActivity, mediatorZoneId, object : RequestAdCallback {
            override fun onAddAvailable(adNetworkName: String) {
                showAd(mediatorZoneId)
            }

            override fun onError(message: String?) {
                log(message)
            }
        })
    }

    private fun showAd(mediatorZoneId: String) {
        AdProvider.showAd(this, mediatorZoneId, object : ShowAdCallback {
            override fun onOpened() {
                log("Opened")
            }

            override fun onClosed() {
                log("onClosed")
            }

            override fun onRewarded() {
                log("onRewarded")
            }

            override fun onError() {
                log("onError")
            }

        })
    }

    private fun log(message: String?) {
        Log.d("MAIN_ACTIVITY_LOG", message.toString())
    }
}