package com.fcossetta.myapplication.main.data.di

import com.fcossetta.myapplication.main.data.ForecastViewModel
import com.fcossetta.myapplication.main.data.api.ApiHelper
import com.fcossetta.myapplication.main.data.api.provideApi
import com.fcossetta.myapplication.main.data.api.provideDefaultOkhttpClient
import com.fcossetta.myapplication.main.data.api.provideRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module   {
    single { provideDefaultOkhttpClient() }
    single { provideRetrofit(client = get()) }
    single { provideApi(get()) }
    viewModel { ForecastViewModel(apiHelper = ApiHelper(get())) }
}