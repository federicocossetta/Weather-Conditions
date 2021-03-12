package com.fcossetta.myapplication.main.data

import com.fcossetta.myapplication.main.data.api.ApiHelper
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.ForecastDailyInfo
import com.fcossetta.myapplication.main.data.model.ForecastList
import com.fcossetta.myapplication.main.ui.ForecastEvent
import com.fcossetta.myapplication.main.ui.ForecastState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import retrofit2.await
import java.util.LinkedHashMap

class ForecastViewModel(private val apiHelper: ApiHelper) :
    AndroidDataFlow(defaultState = UIState.Empty) {

    fun getForecastByCity(cityName: String) = action {
        val response = apiHelper.getForecast(cityName).await()
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<ForecastList>? =
            moshi.adapter(ForecastList::class.java)
        val jsonString = response.string()
        val fromJson = adapter?.fromJson(jsonString)
        if (fromJson != null) {
            sendEvent { ForecastEvent.ForecastsFound(fromJson) }
        }
    }

    fun getWeatherByCity(cityName: String) = action {
        val response = apiHelper.getWeatherConditions(cityName).await()
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<Forecast>? =
            moshi.adapter(Forecast::class.java)
        val jsonString = response.string()
        val fromJson = adapter?.fromJson(jsonString)
        if (fromJson != null) {
            sendEvent { ForecastEvent.WeatherConditionFound(fromJson) }
        }
    }

    fun getDailyForecast(lat: Double?, long: Double?) = action {
        if (lat != null && long != null) {
            val response = apiHelper.getDailyForecast(lat, long).await()
            val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter: JsonAdapter<ForecastDailyInfo>? =
                moshi.adapter(ForecastDailyInfo::class.java)
            val jsonString = response.string()
            val fromJson = adapter?.fromJson(jsonString)
            if (fromJson != null) {
                sendEvent { ForecastEvent.DailyInfoFound(fromJson) }
            }
        }
    }

    fun emitData(
        weather: Forecast?,
        forecastList: LinkedHashMap<String, List<Forecast>>?,
        groupedForecasts: ForecastDailyInfo,
        cityName: String
    ) = action {
        setState { ForecastState.ShowData(forecastList, weather, groupedForecasts, cityName) }
    }
}