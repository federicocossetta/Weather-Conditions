package com.fcossetta.myapplication.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.ShortForecast
import com.fcossetta.myapplication.main.data.model.WeatherDetail
import com.fcossetta.myapplication.main.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.core.context.GlobalContext
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(), WeatherAdapter.CellClickListener {

    val args: HomeFragmentArgs by navArgs()
    val glide: RequestManager by lazy { GlobalContext.get().koin.get() }
    var df: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val forecastDetail = args.forecastDetail
        forecastDetail.let {
            showData(forecastDetail.dailyForecast, forecastDetail.weather, forecastDetail.cityName)
        }
    }

    private fun showData(
        shortInfo: List<ShortForecast>?,
        currentForecast: Forecast?,
        cityNameString: String
    ) {
        cityName.text = cityNameString
        if (currentForecast != null) {
            val weather = currentForecast?.weather?.get(0)
            if (weather != null) {
                val format = String.format(Constants.IMG_URL_BIG, weather.icon)
                glide.load(format).into(icon)
            }
            if (currentForecast.main != null) {
                max.text = currentForecast.main.maxTemp.toString()
                min.text = currentForecast.main.minTemp.toString()
            }
        }
        if (shortInfo != null) {
            // remove current day from forecasts
            val weatherAdapter = WeatherAdapter(shortInfo, this)
            val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecorator.setDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.divider
                )!!
            )
            forecast_container.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(activity)
                // set the custom adapter to the RecyclerView
                adapter = weatherAdapter
                addItemDecoration(itemDecorator)
                setHasFixedSize(true)
            }
        }

    }

    override fun onCellClickListener(data: String) {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val currentDay =
            WeatherDetail(args.forecastDetail.forecasts, data, args.forecastDetail.cityName)
        navHostFragment.navController.navigate(
            HomeFragmentDirections.actionLoadingToDetail(
                currentDay
            )
        )
    }

}

