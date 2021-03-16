package com.fcossetta.myapplication.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.WeatherDetail
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.weather_detail.*

class ForecastsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.weather_detail, container, false)
        return root
    }

    val args: ForecastsFragmentArgs by navArgs()
    override fun onPause() {
        super.onPause()
        onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val forecastsList: WeatherDetail = args.forecastsList
        super.onViewCreated(view, savedInstanceState)
        if (
            activity?.supportFragmentManager != null) {
            val tabs: TabLayout = tabs

            val sectionsPagerAdapter = SectionsPagerAdapter(
                this,
                forecastsList.forecast!!, object : CellClickListener {
                    override fun onCellClickListener(data: Forecast) {
                        val navHostFragment =
                            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
                        val actionWeatherDetailToForecastdetail =
                            ForecastsFragmentDirections.forecastDetailAction(data)
                        navHostFragment.navController.navigate(actionWeatherDetailToForecastdetail)
                    }

                },
            )
            view_pager.adapter = sectionsPagerAdapter
            val iterator: Iterator<*> = forecastsList.forecast.iterator()
            var n = 0
            while (iterator.hasNext()) {
                val entry = iterator.next() as Map.Entry<*, *>
                if (entry.key?.equals(forecastsList.currentDay) == true) {
                    break
                }
                n++
            }
            view_pager.doOnPreDraw { view_pager.currentItem = n }
            TabLayoutMediator(tabs, view_pager) { tab, position ->
                tab.text =  forecastsList.forecast!!.keys.elementAt(position)
            }.attach()
        }
    }

}