package com.fcossetta.myapplication.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.model.WeatherDetail
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.weather_detail.*


/**
 * A placeholder fragment containing a simple view.
 */
class ForecastDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.weather_detail, container, false)
        return root
    }

    val args: ForecastDetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val forecastsList: WeatherDetail = args.forecastsList
        super.onViewCreated(view, savedInstanceState)
        if (
            activity?.supportFragmentManager != null) {
            val sectionsPagerAdapter = SectionsPagerAdapter(
                requireActivity().supportFragmentManager,
                forecastsList.forecast!!, forecastsList.currentCity!!
            )
            view_pager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = tabs
            tabs.setupWithViewPager(view_pager)
            val iterator: Iterator<*> = forecastsList.forecast.iterator()
            var n = 0
            while (iterator.hasNext()) {
                val entry = iterator.next() as Map.Entry<*, *>
                if (entry.key?.equals(forecastsList.currentDay) == true) {
                    break
                }
                n++
            }
            view_pager.currentItem = n

        }
    }


}