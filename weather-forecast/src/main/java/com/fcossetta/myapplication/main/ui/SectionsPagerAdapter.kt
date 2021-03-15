package com.fcossetta.myapplication.main.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fcossetta.myapplication.main.data.model.Forecast


class SectionsPagerAdapter(
    fm: FragmentManager,
    var forecastMap: Map<String, List<Forecast>>,
    var city: String
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val key = forecastMap.keys.elementAt(position)
        return ForecastListFragment.newInstance(
            forecastMap[key],
            city,
        )
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return forecastMap.keys.elementAt(position)
    }

    override fun getCount(): Int {
        return forecastMap.keys.size
    }
}