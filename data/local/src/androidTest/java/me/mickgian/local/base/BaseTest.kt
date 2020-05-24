package me.mickgian.local.base

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.mickgian.local.MultiModDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


private const val DATABASE = "DATABASE"
@RunWith(AndroidJUnit4::class)
abstract class BaseTest: KoinTest {

    @Rule @JvmField val instantExecutorRule = InstantTaskExecutorRule()
    protected val database: MultiModDatabase by inject()

    @Before
    open fun setUp(){
        loadKoinModules(listOf(getLocalModuleTest()))
    }

    @After
    open fun tearDown() {
        database.close()
        stopKoin()
    }

    private fun getLocalModuleTest() = module(override = true) {
        single {
            Room.inMemoryDatabaseBuilder(
                get(),
                MultiModDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        }
        factory { get<MultiModDatabase>().userDao() }
    }
}