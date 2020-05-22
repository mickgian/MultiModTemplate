package me.mickgian.multimod.di

import me.mickgian.detail.di.featureDetailModule
import me.mickgian.home.di.featureHomeModule
import me.mickgian.local.di.localModule
import me.mickgian.remote.di.createRemoteModule
import me.mickgian.repository.di.repositoryModule


val appComponent= listOf(
    createRemoteModule("https://api.github.com/"),
    repositoryModule,
    featureHomeModule,
    featureDetailModule,
    localModule
)