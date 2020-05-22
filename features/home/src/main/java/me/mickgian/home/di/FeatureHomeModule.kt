package me.mickgian.home.di

import me.mickgian.home.HomeViewModel
import me.mickgian.home.domain.GetTopUsersUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    factory { GetTopUsersUseCase(get()) }
    viewModel { HomeViewModel(get(), get()) }
}