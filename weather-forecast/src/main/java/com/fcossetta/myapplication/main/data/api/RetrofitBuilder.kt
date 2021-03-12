package com.fcossetta.myapplication.main.data.api

import com.fcossetta.myapplication.main.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


fun provideDefaultOkhttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .build()
}

fun provideApi(retrofit: Retrofit): ForecastService = retrofit.create(ForecastService::class.java)


