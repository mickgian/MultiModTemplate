package me.mickgian.local.di

import me.mickgian.local.MultiModDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE = "DATABASE"

val localModule = module {
    single { MultiModDatabase.buildDatabase(androidContext()) }
    factory { (get() as MultiModDatabase).userDao() }
}