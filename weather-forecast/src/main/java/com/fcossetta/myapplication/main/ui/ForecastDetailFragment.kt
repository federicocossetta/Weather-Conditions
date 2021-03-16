package com.fcossetta.myapplication.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.SharedViewModel
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.utils.CommonFunctions
import com.fcossetta.myapplication.main.utils.Constants
import kotlinx.android.synthetic.main.fragment_forecast_detail.*
import org.koin.core.context.GlobalContext
import java.text.SimpleDateFormat
import java.util.*


class ForecastDetailFragment : Fragment() {

    val args: ForecastDetailFragmentArgs by navArgs()
    private val glide: RequestManager by lazy { GlobalContext.get().koin.get() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showForecastDetail(args.forecast)


    }

    @SuppressLint("SetTextI18n")
    private fun showForecastDetail(forecast: Forecast) {
        forecast.weather?.get(0)?.let {
            val format = String.format(Constants.IMG_URL_BIG, it.icon)
            glide.load(format).into(icon)
            weather_condition.text = it.description
        }
        time.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(forecast.dt!!*1000))
        forecast.main?.let {
            temp.text = CommonFunctions.formatTemp(it.temp)
            val tempMin = CommonFunctions.formatTemp(it.minTemp)
            val tempMax = CommonFunctions.formatTemp(it.maxTemp)
            minMax.text = "$tempMin / $tempMax"
            feel.text = CommonFunctions.formatTemp(it.feelsLike)
            humidity.text = it.humidity.toString()
            pressure.text = it.pressure.toString()
        }
        forecast.clouds?.let {
            clouds.text = it.all.toString()
        }
        forecast.rain?.let {
            val threeHourRainPercentage = it.threeHourRainPercentage
            rain.text = "$threeHourRainPercentage "
        }
        visibility.text = forecast.visibility.toString()
        forecast.wind?.let {
            val deg = it.deg
            val speed = it.speed
            wind.text = "$speed / $deg"
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast_detail, container, false)
    }


}