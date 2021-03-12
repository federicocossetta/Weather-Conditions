package com.fcossetta.myapplication.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.model.Forecast
import java.io.Serializable

/**
 * A fragment representing a list of Items.
 */
class ForecastListFragment : Fragment() {

    private lateinit var forecasts: List<Forecast>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            forecasts = it.getSerializable(FORECASTS) as List<Forecast>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast_list_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyItemRecyclerViewAdapter(forecasts)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val FORECASTS = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(forecasts: List<Forecast>?, city: String) =
            ForecastListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(FORECASTS, forecasts as Serializable)
                }
            }
    }
}