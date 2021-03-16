package com.fcossetta.myapplication.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fcossetta.myapplication.R
import com.fcossetta.myapplication.main.data.model.ShortForecast
import com.fcossetta.myapplication.main.utils.CommonFunctions
import com.fcossetta.myapplication.main.utils.Constants.Companion.IMG_URL_BIG
import kotlinx.android.synthetic.main.weather_item_list.view.*
import org.koin.core.context.GlobalContext
import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter(
    private var dataList: List<ShortForecast>,
    private val cellClickListener: CellClickListener,
    var df: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {

    override fun getItemCount(): Int = dataList.size
    private val glide: RequestManager by lazy { GlobalContext.get().koin.get() }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item_list, parent, false)
        return MyViewHolder(view, glide, df)
    }

    class MyViewHolder(
        itemView: View,
        var glide: RequestManager,
        var df: SimpleDateFormat,
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(current: ShortForecast, format: String) {
            itemView.day.text = format
            if (current.weather?.get(0) != null) {
                val format = String.format(IMG_URL_BIG, current.weather.get(0).icon)
                glide.load(format).into(itemView.forecast)
            }

            itemView.min.text = CommonFunctions.formatTemp(current.temp?.min)
            itemView.max.text = CommonFunctions.formatTemp(current.temp?.max)
        }
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = dataList[position]
        val format = df.format(current.dt?.times(1000))
        holder.bind(current, format)
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(format)
        }
    }

    interface CellClickListener {
        fun onCellClickListener(data: String)
    }

}

