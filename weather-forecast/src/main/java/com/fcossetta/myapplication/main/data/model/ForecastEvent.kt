package com.fcossetta.myapplication.main.data.model

import io.uniflow.core.flow.data.UIEvent

open class ForecastEvent : UIEvent() {
    data class ForecastsFound(val forecasts: ForecastList) :
        ForecastEvent()
    data class WeatherConditionFound(val weather: Forecast) :
        ForecastEvent()
    data class Error(val city: String?) :
        ForecastEvent()
    data class DailyInfoFound(val forecasts: ForecastDailyInfo) :
        ForecastEvent()

}