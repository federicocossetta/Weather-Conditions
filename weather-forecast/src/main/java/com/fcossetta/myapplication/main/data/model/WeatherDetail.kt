package com.fcossetta.myapplication.main.data.model

import java.io.Serializable

data class WeatherDetail(
    val forecast: LinkedHashMap<String, List<Forecast>>?,
    val currentDay: String,
    val currentCity: String
) : Serializable
