package com.example.admediator.data

import com.example.admediator.model.AppSetting
import com.example.admediator.model.MediationGroup

interface IRepository {

    fun getAppSetting(appId: String) : AppSetting
    fun getMediationGroup(mediatorZoneId: String) : MediationGroup?
    fun cacheMediationGroup(mediatorZoneId: String, mediationGroup: MediationGroup?)

}