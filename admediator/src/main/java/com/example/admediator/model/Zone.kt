package com.example.admediator.model

import com.example.admediator.AdType

data class Zone(
    val type: AdType,
    val waterfall: Waterfall
)