package com.example.admediator

import com.example.admediator.data.RepositoryImpl
import com.example.admediator.model.AppSetting

class AppSettingManager(private val repositoryImpl: RepositoryImpl) {

    fun getAppSetting(appId: String) : AppSetting? {
        return repositoryImpl.getAppSetting(appId)
    }

}