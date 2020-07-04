package com.example.tapsell_sdk_android

import android.util.Log
import androidx.annotation.Keep

@Keep
class AdapterTest {


    fun printAdapter() {
        Log.d("TEST_MODULE", "Adapter printer called")

    }

    companion object {

        private var adapter : AdapterTest? = null
        @Synchronized
        fun get(): AdapterTest {
            if (adapter == null) {
                adapter = AdapterTest()
            }
            Log.d("TEST_MODULE", "GET METHOD OF COMPANION OBJECT CALLED called")
            return adapter!!
        }
    }
}

@Keep
object AdapterTestSingleton {

    fun printAdapter() {
        Log.d("TEST_MODULE", "Singleton Adapter printer called")
    }
}

