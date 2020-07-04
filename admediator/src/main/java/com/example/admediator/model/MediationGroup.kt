package com.example.admediator.model

class MediationGroup (
    val zoneType: String,
    val waterfalls : List<Waterfall>,
    val ttl: Long
)

data class Waterfall (
    val adNetwork: String,
    val zoneId: String,
    val timeout: Long
)