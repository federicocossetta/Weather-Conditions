package com.fcossetta.myapplication

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fcossetta.myapplication.main.data.ForecastViewModel
import com.fcossetta.myapplication.main.data.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.uniflow.androidx.flow.onEvents
import io.uniflow.androidx.flow.onStates
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG: String = "LOG"
    private lateinit var findItem: MenuItem
    private lateinit var searchManager: SearchManager
    private lateinit var navController: NavController
    private val viewModel: ForecastViewModel by viewModel()
    var groups: LinkedHashMap<String, List<Forecast>>? = null
    var city: City? = null
    var weather: Forecast? = null
    var forecastList: ForecastList? = null
    lateinit var cityName: String
    private lateinit var formatOut: SimpleDateFormat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        setContentView(R.layout.activity_main)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        fab.setOnClickListener { view ->
            findItem.isVisible = true
            (findItem.actionView as SearchView).apply {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                setIconifiedByDefault(false)
                requestFocus()
            }
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }
        formatOut = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        loading.setOnTouchListener(object : View.OnClickListener, View.OnTouchListener {
            override fun onClick(v: View?) {
                // do nothing
            }

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return false
            }


        })
        onStates(viewModel) {
            when (val event = it) {
                is InterfaceState.LoadingStatus -> {
                    fab.isEnabled = !event.loading
                    if (event.loading) {
                        loading.visibility = View.VISIBLE
                    } else {
                        loading.visibility = View.GONE

                    }

                }
            }
        }
        onEvents(viewModel) {

            val instance = Calendar.getInstance()
            instance.time = Date(System.currentTimeMillis())
            instance.add(Calendar.DATE, 4)
            instance.set(Calendar.HOUR, 23)
            instance.set(Calendar.MINUTE, 59)
            when (val event = it.take()) {
                is ForecastEvent.WeatherConditionFound -> {
                    weather = event.weather
                    viewModel.getForecastByCity(cityName)
                }

                is ForecastEvent.ForecastsFound -> {
                    forecastList = event.forecasts
                    if (forecastList != null) {
                        city = event.forecasts.city
                        val formatIn = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        //remove today forecast
                        val filter = forecastList!!.forecasts?.filter { forecast ->
                            val times = forecast.dt?.times(1000)
                            times == null || Date(times).before(
                                instance.time
                            )

                        }
                        groups = filter?.groupBy { item ->
                            val date = formatIn.parse(item.displayTime!!)
                            formatOut.format(date)

                        } as LinkedHashMap<String, List<Forecast>>?
                        viewModel.getDailyForecast(
                            city?.coord?.lat,
                            city?.coord?.lon
                        )
                    }
                }
                is ForecastEvent.Error -> {
                    val cityRequested = event.city
                    //TODO: BETTER ERROR HANDING
                    viewModel.action { setState { InterfaceState.LoadingStatus(false) } }
                    var error =
                        if (cityRequested != null) "No city found with name $cityRequested. Please enter the full city name!" else "Generic Error"
                    Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
                }
                is ForecastEvent.DailyInfoFound -> {
                    val dailyForecast: ForecastDailyInfo = event.forecasts
                    val filter = dailyForecast.forecasts?.filter { forecast ->
                        val times = forecast.dt?.times(1000)
                        times == null || Date(times).before(
                            instance.time
                        )
                    }
                    val currentDay = ForecastDetail(groups, weather, filter)
                    title = cityName.capitalize(Locale.getDefault())
                    (findItem.actionView as SearchView).apply {
                        setQuery("", false);
                        isIconified = true;
                    }
                    findItem.isVisible = false
                    viewModel.action { setState { InterfaceState.LoadingStatus(false) } }
                    navHostFragment.navController.navigate(
                        NavGraphDirections.actionLoadData(
                            currentDay
                        )
                    )

                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                cityName = query
                viewModel.getWeatherByCity(cityName.trim())
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        if (menu != null) {
            findItem = menu.findItem(R.id.menu_search)
            var searchView = findItem.actionView as SearchView
            searchView.apply {
                // Assumes current activity is the searchable activity
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                focusable = View.FOCUSABLE
                setIconifiedByDefault(true) // Do not iconify the widget; expand it by default
            }

        }
        return true
    }


}