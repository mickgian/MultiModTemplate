package me.mickgian.repository.di

import kotlinx.coroutines.Dispatchers
import me.mickgian.repository.AppDispatchers
import me.mickgian.repository.UserRepository
import me.mickgian.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory { UserRepositoryImpl(get(), get()) as UserRepository }
}