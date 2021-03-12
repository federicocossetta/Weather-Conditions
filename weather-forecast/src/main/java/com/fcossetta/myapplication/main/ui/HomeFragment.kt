package com.fcossetta.myapplication.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.ForecastViewModel
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.ForecastDailyInfo
import com.fcossetta.myapplication.main.data.model.WeatherDetail
import com.fcossetta.myapplication.main.utils.Constants
import io.uniflow.androidx.flow.onStates
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.max
import kotlinx.android.synthetic.main.fragment_home.min
import kotlinx.android.synthetic.main.weather_item_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.context.GlobalContext
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), WeatherAdapter.CellClickListener {
    private lateinit var cityStringName: String
    private var forecasts: LinkedHashMap<String, List<Forecast>>? = null


    // TODO: Rename and change types of parameters
    private val viewModel: ForecastViewModel by sharedViewModel()
    private var param1: String? = null
    private var param2: String? = null
    val glide: RequestManager by lazy { GlobalContext.get().koin.get<RequestManager>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onStates(viewModel) { state ->
            when (state) {
                is ForecastState.ShowData -> {
                    forecasts = state.forecasts
                    cityStringName = state.cityName
                    showData(state.shortInfo, state.weather, cityStringName)
                }

            }

        }
    }

    private fun showData(
        shortInfo: ForecastDailyInfo,
        currentForecast: Forecast?,
        cityNameString: String
    ) {
        cityName.text = cityNameString
        if (currentForecast != null) {
            val weather = currentForecast?.weather?.get(0)
            if (weather != null) {
                val format = String.format(Constants.IMG_URL_BIG, weather.icon)
                glide.load(format).centerCrop().into(icon)
            }
            if (currentForecast.main != null) {
                max.text = currentForecast.main.tempMax.toString()
                min.text = currentForecast.main.tempMin.toString()
            }
        }
        if (shortInfo.forecasts != null) {
            val weatherAdapter = WeatherAdapter(shortInfo.forecasts, this)
            forecast_container.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(activity)
                // set the custom adapter to the RecyclerView
                adapter = weatherAdapter
            }
        }

    }

    override fun onCellClickListener(data: String) {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navHostFragment.childFragmentManager.fragments[0]
        val currentDay = WeatherDetail(forecasts, data, cityStringName)
        navHostFragment.navController.navigate(HomeFragmentDirections.actionLoadingToDetail(currentDay))
    }
}

