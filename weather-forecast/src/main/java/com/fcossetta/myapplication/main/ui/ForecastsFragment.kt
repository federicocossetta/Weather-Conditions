package com.fcossetta.myapplication.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.SharedViewModel
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.WeatherDetail
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.weather_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ForecastsFragment : Fragment() {
    var day: String? = null
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
        var viewModel =
            ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        viewModel.bundleFromFragmentBToFragmentA.observe(viewLifecycleOwner, Observer {
            day = it.getString("ARGUMENT_MESSAGE", "aa")
            if (activity?.supportFragmentManager != null) {
                val tabs: TabLayout = tabs

                val sectionsPagerAdapter = SectionsPagerAdapter(
                    this,
                    forecastsList.forecast!!,
                    object : CellClickListener {
                        override fun onCellClickListener(data: Forecast) {
                            val navHostFragment =
                                requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
                            GlobalScope.launch(Dispatchers.Main) {
                                val bundle = Bundle().apply { putString("ARGUMENT_MESSAGE", day) }
                                viewModel.bundleFromFragmentBToFragmentA.value = bundle
                            }
                            val actionWeatherDetailToForecastdetail =
                                ForecastsFragmentDirections.forecastDetailAction(data)
                            navHostFragment.navController.navigate(
                                actionWeatherDetailToForecastdetail
                            )
                        }

                    },
                )
                view_pager.adapter = sectionsPagerAdapter
                val iterator: Iterator<*> = forecastsList.forecast.iterator()
                var n = 0
                while (iterator.hasNext()) {
                    val entry = iterator.next() as Map.Entry<*, *>
                    if (entry.key?.equals(day) == true) {
                        break
                    }
                    n++
                }
                view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        val elementAt = forecastsList.forecast.keys.elementAt(position)
                        day = elementAt

                    }

                    override fun onPageSelected(position: Int) {
                        val elementAt = forecastsList.forecast.keys.elementAt(position)
                        day = elementAt
                    }
                })
                view_pager.doOnPreDraw { view_pager.setCurrentItem(n, false) }
                TabLayoutMediator(tabs, view_pager) { tab, position ->
                    val elementAt = forecastsList.forecast.keys.elementAt(position)
                    tab.text = elementAt.substring(0,elementAt.lastIndexOf("-"))
                }.attach()
            }
        })

    }

}