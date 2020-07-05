package com.example.admediator.model

import com.google.gson.annotations.SerializedName

data class AppSetting(
    @SerializedName("adNetworks")
    val adNetworks: AdNetworks
)