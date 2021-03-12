package com.fcossetta.myapplication.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.utils.Constants
import kotlinx.android.synthetic.main.weather_item_list.view.*
import org.koin.core.context.GlobalContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<Forecast>,
    private val sdf: SimpleDateFormat = SimpleDateFormat("hh aa", Locale.getDefault())
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    private val glide: RequestManager by lazy { GlobalContext.get().koin.get() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.day.text = sdf.format(item.dt?.times(1000))
        holder.temp.text = item.main?.temp.toString()
        holder.description.text = item.weather?.get(0)?.description.toString()
        val iconUrl = String.format(Constants.IMG_URL, item.weather?.get(0)?.icon)
        glide.load(iconUrl).into(holder.icon)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val temp: TextView = view.findViewById(R.id.temp)
        val day: TextView = view.findViewById(R.id.day)
        val icon: ImageView = view.findViewById(R.id.image_forecast)
        val description: TextView = view.findViewById(R.id.description)

    }
}