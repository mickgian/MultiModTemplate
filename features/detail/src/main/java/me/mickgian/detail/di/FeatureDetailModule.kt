package me.mickgian.detail.di

import me.mickgian.detail.DetailImageViewModel
import me.mickgian.detail.DetailViewModel
import me.mickgian.detail.domain.GetUserDetailUseCase
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val featureDetailModule = module {
    factory { GetUserDetailUseCase(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { DetailImageViewModel() }
}