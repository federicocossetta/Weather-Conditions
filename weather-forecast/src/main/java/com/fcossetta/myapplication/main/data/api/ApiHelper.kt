package com.fcossetta.myapplication.main.data.api

import com.fcossetta.myapplication.BuildConfig.API_KEY
import okhttp3.ResponseBody
import retrofit2.Call


class ApiHelper(private val apiService: ForecastService) {

    fun getForecast(cityName: String): Call<ResponseBody> =
        apiService.getForecast(cityName, API_KEY,"metric")

    fun getDailyForecast(lat: Double, long: Double): Call<ResponseBody> =
        apiService.getDaily(lat, long, API_KEY, "metric","minutely,hourly,current,alerts")


    fun getWeatherConditions(city: String): Call<ResponseBody> =
        apiService.getWeather(city, API_KEY,"metric")
}