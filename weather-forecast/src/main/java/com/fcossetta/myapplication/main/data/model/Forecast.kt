package com.fcossetta.myapplication.main.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


@JsonClass(generateAdapter = true)
data class ForecastList(
    @Json(name = "cod")
    val cod: Int? = null,
    @Json(name = "message")
    val message: Int? = null,
    @Json(name = "cnt")
    val cnt: Int? = null,
    @Json(name = "city")
    val city: City? = null,
    @Json(name = "list")
    val forecasts: List<Forecast>? = null
)

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "base")
    val base: String? = null,
    @Json(name = "visibility")
    val visibility: Long? = null,
    @Json(name = "dt")
    val dt: Long? = null,
    @Json(name = "timezone")
    val timezone: Int? = null,
    @Json(name = "id")
    val id: Long? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "cod")
    val cod: Int? = null,
    @Json(name = "coord")
    val coord: Coord? = null,
    @Json(name = "weather")
    val weather: List<Weather>? = null,
    @Json(name = "main")
    val main: Main? = null,
    @Json(name = "wind")
    val wind: Wind? = null,
    @Json(name = "clouds")
    val clouds: Clouds? = null,
    @Json(name = "rain")
    val rain: Rain? = null,
    @Json(name = "dt_txt")
    val displayTime: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "main")
    val main: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "icon")
    val icon: String? = null,
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: Double? = null,
    @Json(name = "feels_like")
    val feelsLike: Double? = null,
    @Json(name = "temp_min")
    val minTemp: Double? = null,
    @Json(name = "temp_max")
    val maxTemp: Double? = null,
    @Json(name = "pressure")
    val pressure: Double? = null,
    @Json(name = "humidity")
    val humidity: Double? = null
)

@JsonClass(generateAdapter = true)
data class Coord(
    @Json(name = "lat")
    val lat: Double? = null,
    @Json(name = "lon")
    val lon: Double? = null
)

@JsonClass(generateAdapter = true)
data class Clouds(
    @Json(name = "all")
    val all: Int? = null
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed")
    val speed: Double? = null,
    @Json(name = "deg")
    val deg: Int? = null
)

@JsonClass(generateAdapter = true)
data class City(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "coord")
    val coord: Coord? = null,
    @Json(name = "country")
    val country: String? = null,
    @Json(name = "population")
    val population: Long? = null,
    @Json(name = "sunrise")
    val sunrise: Long? = null,
    @Json(name = "sunset")
    val sunset: Long? = null,
    @Json(name = "timezone")
    val timezone: Int? = null
) : Serializable


@JsonClass(generateAdapter = true)
data class Rain(
    @Json(name = "3h")
    val threeHourRainPercentage: String? = null,
)

