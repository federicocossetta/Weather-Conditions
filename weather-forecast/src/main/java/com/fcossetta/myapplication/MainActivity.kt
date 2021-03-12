package com.fcossetta.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fcossetta.myapplication.main.data.model.City
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.ForecastDailyInfo
import com.fcossetta.myapplication.main.data.model.ForecastList
import com.fcossetta.myapplication.main.ui.ForecastEvent
import com.fcossetta.myapplication.main.data.ForecastViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.uniflow.androidx.flow.onEvents
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val viewModel: ForecastViewModel by viewModel()
    var groups: LinkedHashMap<String, List<Forecast>>? = null
    var city: City? = null
    var weather: Forecast? = null
    var forecastList: ForecastList? = null
    var cityName: String = "London"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            viewModel.getWeatherByCity(cityName)
        }
        val formatOut = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        onEvents(viewModel) {
            when (val event = it.take()) {
                is ForecastEvent.WeatherConditionFound -> {
                    weather = event.weather
                    viewModel.getForecastByCity(cityName)
                }

                is ForecastEvent.ForecastsFound -> {
                    forecastList = event.forecasts
                    if (forecastList != null) {
                        city = event.forecasts.city
                        val formatIn = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
                        groups = forecastList!!.forecasts?.groupBy { item ->
                            val date = formatIn.parse(item.displayTime)
                            formatOut.format(date)

                        } as LinkedHashMap<String, List<Forecast>>?
                        viewModel.getDailyForecast(
                            city?.coord?.lat,
                            city?.coord?.lon
                        )
                    }
                }
                is ForecastEvent.DailyInfoFound -> {
                    val dailyForecast: ForecastDailyInfo = event.forecasts
                    viewModel.emitData(weather, groups, dailyForecast, cityName)

                }
            }
        }
    }
}