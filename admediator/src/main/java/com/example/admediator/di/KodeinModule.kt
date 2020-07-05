package com.example.admediator.di

import com.example.admediator.AppSettingManager
import com.example.admediator.MediationGroupManager
import com.example.admediator.data.IRepository
import com.example.admediator.data.LocalRepository
import com.example.admediator.data.NetworkRepository
import com.example.admediator.data.RepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


val myKodein = Kodein {
    bind<IRepository>(tag = "Network") with singleton { NetworkRepository() }
    bind<IRepository>(tag = "Local") with singleton { LocalRepository() }
    bind<RepositoryImpl>() with singleton { RepositoryImpl(instance(tag = "Network"), instance(tag = "Local")) }
    bind<MediationGroupManager>() with singleton { MediationGroupManager(instance()) }
    bind<AppSettingManager>() with singleton { AppSettingManager(instance()) }
}