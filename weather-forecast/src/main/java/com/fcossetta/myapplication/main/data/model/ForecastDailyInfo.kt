package com.fcossetta.myapplication.main.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class ForecastDailyInfo(
    @Json(name = "daily")
    val forecasts: List<ShortForecast>? = null

)

@JsonClass(generateAdapter = true)
data class ShortForecast(
    @Json(name = "temp")
    val temp: Temp? = null,
    @Json(name = "weather")
    val weather: List<ShortWeather>? = null,
    @Json(name = "dt")
    val dt: Long? = null,
    @Json(name = "pressure")
    val pressure: Int? = null,
    @Json(name = "humidity")
    val humidity: Int? = null,
    @Json(name = "wind_speed")
    val windSpeed: Double? = null,
    @Json(name = "wind_deg")
    val windDeg: Double? = null,
) : Serializable

@JsonClass(generateAdapter = true)
data class ShortWeather(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "main")
    val main: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "icon")
    val icon: String? = null
)

@JsonClass(generateAdapter = true)
data class Temp(
    @Json(name = "min")
    val min: Double? = null,
    @Json(name = "max")
    val max: Double? = null
)