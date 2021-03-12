package com.fcossetta.myapplication.main.data.di

import com.fcossetta.myapplication.main.data.api.ApiHelper
import com.fcossetta.myapplication.main.data.api.provideApi
import com.fcossetta.myapplication.main.data.api.provideDefaultOkhttpClient
import com.fcossetta.myapplication.main.data.api.provideRetrofit
import com.fcossetta.myapplication.main.data.ForecastViewModel
import org.koin.dsl.module

val networkModule = module   {
    single { provideDefaultOkhttpClient() }
    single { provideRetrofit(client = get()) }
    single { provideApi(get()) }
    single { ForecastViewModel(apiHelper = ApiHelper(get())) }
}