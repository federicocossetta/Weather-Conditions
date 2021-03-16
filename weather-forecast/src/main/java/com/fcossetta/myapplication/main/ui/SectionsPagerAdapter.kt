package com.fcossetta.myapplication.main.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fcossetta.myapplication.main.data.model.Forecast


class SectionsPagerAdapter(
     fm: Fragment,
    var forecastMap: Map<String, List<Forecast>>,
    var listener: CellClickListener,
) : FragmentStateAdapter(fm) {


    override fun getItemCount(): Int {
        return forecastMap.keys.size
    }

    override fun createFragment(position: Int): Fragment {
        val key = forecastMap.keys.elementAt(position)
        return ForecastListFragment(
            forecastMap[key], listener
        )
    }
}


