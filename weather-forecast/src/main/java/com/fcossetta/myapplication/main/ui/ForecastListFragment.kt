package com.fcossetta.myapplication.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.ForecastViewModel
import com.fcossetta.myapplication.main.data.model.Forecast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ForecastListFragment(var forecasts: List<Forecast>?, var clickListener: CellClickListener) :
    Fragment() {

    private val viewModel: ForecastViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast_list_list, container, false)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        forecasts?.let {
            val myItemRecyclerViewAdapter =
                MyItemRecyclerViewAdapter(it, viewModel, clickListener)
            // Set the adapter
            if (view is RecyclerView) {
                with(view) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = myItemRecyclerViewAdapter
                    addItemDecoration(itemDecorator)
                    setHasFixedSize(true)
                }
            }
        }

        return view
    }


}