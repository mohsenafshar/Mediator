package com.example.testtapsell

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.admediator.AdProvider
import com.example.admediator.RequestAdCallback
import com.example.admediator.ShowAdCallback
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val MEDIATOR_APP_ID = "MEDIATOR_APP_ID"
        const val MEDIATOR_ZONE_ID_1 = "MEDIATOR_ZONE_ID_1"
        const val MEDIATOR_ZONE_ID_2 = "MEDIATOR_ZONE_ID_2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        AdProvider.initialize(this@MainActivity, MEDIATOR_APP_ID)
        Handler().postDelayed({
            requestAd(MEDIATOR_ZONE_ID_1)
            requestAd(MEDIATOR_ZONE_ID_2)
        }, 2)
    }

    private fun requestAd(mediatorZoneId: String) {
        AdProvider.requestAd(this@MainActivity, mediatorZoneId, object : RequestAdCallback {
            override fun onAddAvailable(adNetworkName: String) {
                log("Ad Provided from " + adNetworkName)
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

            override fun onError(message: String?) {
                log("onError " + message)
            }

        })
    }

    private fun log(message: String?) {
        Log.d("MAIN_ACTIVITY_LOG", message.toString())
    }
}