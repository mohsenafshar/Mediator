package com.example.admediator

import android.util.Log
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

class MediatorKotlinTest  {
    fun print() {
        Log.d("TEST_MODULE", "MediatorTest printed")

        val kclass: KClass<*> = Class.forName("com.example.tapsell_sdk_android.AdapterTest").kotlin
        val instance = kclass.createInstance()
        val method = kclass.java.getMethod("printAdapter")
        method.invoke(instance)

        val companionKclass = kclass.companionObject
        val companionInstance = kclass.companionObjectInstance
        val getMethod = companionKclass?.java?.getMethod("get")
        getMethod?.invoke(companionInstance)
    }
}