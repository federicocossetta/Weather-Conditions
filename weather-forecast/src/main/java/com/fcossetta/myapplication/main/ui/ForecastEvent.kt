package com.fcossetta.myapplication.main.ui

import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.ForecastDailyInfo
import com.fcossetta.myapplication.main.data.model.ForecastList
import io.uniflow.core.flow.data.UIEvent

open class ForecastEvent : UIEvent() {
    data class ForecastsFound(val forecasts: ForecastList) :
        ForecastEvent()
    data class WeatherConditionFound(val weather: Forecast) :
        ForecastEvent()
    data class DailyInfoFound(val forecasts: ForecastDailyInfo) :
        ForecastEvent()

}