package com.example.admediator;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MediatorTestClass {
    public void print() {
        Log.d("TEST_MODULE", "MediatorTest printed");

        try {
            Class<?> adapterTestClass = Class.forName("com.example.tapsell_sdk_android.AdapterTest");
            Constructor<?> constructor = adapterTestClass.getConstructor();
            Method printAdapter = adapterTestClass.getMethod("printAdapter");
            Object newInstance = constructor.newInstance();
            printAdapter.invoke(newInstance);

//            Method staticMethod = adapterTestSingletoneClass.getMethod("printAdapter");

            Class<?> adapterTestComanionClass = Class.forName("com.example.tapsell_sdk_android.AdapterTest$Companion");
//            Constructor<?> companionConstructor = adapterTestComanionClass.getConstructor(DefaultConstructorMarker.class);
            Method get = adapterTestComanionClass.getMethod("get");
            Object companionInstance = adapterTestComanionClass.newInstance();
            get.invoke(companionInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
