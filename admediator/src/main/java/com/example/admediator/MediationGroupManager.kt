package com.example.admediator

import com.example.admediator.data.RepositoryImpl
import com.example.admediator.model.MediationGroup
import com.example.admediator.model.Waterfall
import com.example.admediator.model.Zone
import java.util.*

class MediationGroupManager(private val repository: RepositoryImpl) {

    fun getMediationGroup(mediatorZoneId: String): MediationGroup? {
        return repository.getMediationGroup(mediatorZoneId)
    }
}

