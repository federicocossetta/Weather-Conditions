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
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.utils.Constants
import kotlinx.android.synthetic.main.fragment_forecast_detail.*
import org.koin.core.context.GlobalContext


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
        forecast.main?.let {
            temp.text = it.temp.toString()
            val tempMin = it.minTemp.toString()
            val tempMax = it.maxTemp.toString()
            minMax.text = "$tempMin / $tempMax"
            feel.text = it.feelsLike.toString()
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