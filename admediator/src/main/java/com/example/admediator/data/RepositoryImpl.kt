package com.example.admediator.data

import com.example.admediator.model.AppSetting
import com.example.admediator.model.MediationGroup
import java.lang.Exception


class RepositoryImpl (private val networkRepository: IRepository, private val localRepository: IRepository): IRepository {
    override fun getAppSetting(appId: String) : AppSetting{
        return networkRepository.getAppSetting(appId)
    }

    override fun getMediationGroup(mediatorZoneId: String): MediationGroup? {
        val cached = localRepository.getMediationGroup(mediatorZoneId)
        if (cached != null && cached.isValid()) {
            return cached
        }
        val mediationGroup =  networkRepository.getMediationGroup(mediatorZoneId)
        localRepository.cacheMediationGroup(mediatorZoneId, mediationGroup)
        return mediationGroup
    }

    override fun cacheMediationGroup(mediatorZoneId: String, mediationGroup: MediationGroup?) {
        throw Exception("This method should not be used directly")
    }

}