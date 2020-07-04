package com.example.testtapsell

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.admediator.*
import com.example.tapsell_sdk_android.AdapterTest


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        MediatorTestClass().print()

        MediatorKotlinTest().print()

        AdProvider.initialize(this@MainActivity, "YOUR_APP_ID")
        AdProvider.requestAd(this@MainActivity, "YOUR_ZONE_ID", object : RequestAdCallback {
            override fun onAddAvailable(adId: String) {
                showAd(adId)
            }

            override fun onError(message: String?) {

            }

        })
    }

    private fun showAd(adId: String) {
        AdProvider.showAd(this, adId, "YOUR_ZONE_ID", object : ShowAdCallback {
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