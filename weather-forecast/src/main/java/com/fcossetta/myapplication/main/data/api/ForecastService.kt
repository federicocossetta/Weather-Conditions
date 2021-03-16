package com.fcossetta.myapplication.main.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastService {

    @GET("/data/2.5/forecast")
    fun getForecast(
        @Query("q") cityName: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
    ): Call<ResponseBody>

    @GET("/data/2.5/onecall")
    fun getDaily(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String,
        @Query("exclude", encoded = true) exclude: String,
    ): Call<ResponseBody>

    @GET("/data/2.5/weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): Call<ResponseBody>
}