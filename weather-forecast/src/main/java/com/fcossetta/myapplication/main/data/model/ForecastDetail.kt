package com.fcossetta.myapplication.main.data.model

import java.io.Serializable
import java.util.*

data class ForecastDetail(
    val forecasts: LinkedHashMap<String, List<Forecast>>?,
    val weather: Forecast?,
    val dailyForecast: ForecastDailyInfo,
    val cityName: String
) : Serializable
