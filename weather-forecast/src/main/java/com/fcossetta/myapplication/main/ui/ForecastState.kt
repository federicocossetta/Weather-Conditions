package com.fcossetta.myapplication.main.ui

import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.ForecastDailyInfo
import io.uniflow.core.flow.data.UIState
import java.util.*

open class ForecastState : UIState() {
    data class ShowData(
        val forecasts: LinkedHashMap<String, List<Forecast>>?,
        val weather: Forecast?,
        val shortInfo: ForecastDailyInfo,
        val cityName: String
    ) :
        ForecastState()

}