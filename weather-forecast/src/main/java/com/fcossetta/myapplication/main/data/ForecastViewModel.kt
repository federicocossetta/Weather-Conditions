package com.fcossetta.myapplication.main.data

import android.content.ContentValues.TAG
import android.util.Log
import com.fcossetta.myapplication.main.data.api.ApiHelper
import com.fcossetta.myapplication.main.data.model.Forecast
import com.fcossetta.myapplication.main.data.model.ForecastDailyInfo
import com.fcossetta.myapplication.main.data.model.ForecastList
import com.fcossetta.myapplication.main.data.model.ForecastEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastViewModel(private val apiHelper: ApiHelper) :
    AndroidDataFlow(defaultState = UIState.Empty) {


    fun getForecastByCity(cityName: String) {
        apiHelper.getForecast(cityName).enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                try {
                    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val adapter: JsonAdapter<ForecastList>? =
                        moshi.adapter(ForecastList::class.java)
                    val jsonString = response.body()
                    jsonString?.string()?.let {
                        val forecastList = adapter?.fromJson(it)
                        if (forecastList != null) {
                            action {
                                sendEvent { ForecastEvent.ForecastsFound(forecastList) }
                            }
                        }
                    }
                } catch (c: java.lang.Exception) {
                    action {
                        sendEvent { ForecastEvent.Error(cityName) }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                action {
                    sendEvent { ForecastEvent.Error(cityName) }
                }
            }
        })


    }

    fun getWeatherByCity(cityName: String) {
        apiHelper.getWeatherConditions(cityName).enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                try {
                    if (response.isSuccessful) {
                        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                        val adapter: JsonAdapter<Forecast>? =
                            moshi.adapter(Forecast::class.java)
                        val jsonString = response.body()
                        jsonString?.string()?.let {
                            val fromJson = adapter?.fromJson(it)
                            if (fromJson != null) {
                                action {
                                    sendEvent { ForecastEvent.WeatherConditionFound(fromJson) }
                                }
                            }
                        }
                    } else {
                        if (response.code() == 404) {
                            action {
                                sendEvent { ForecastEvent.Error(cityName) }
                            }
                        }
                    }

                } catch (c: java.lang.Exception) {
                    Log.e(TAG, "onResponse: ")
                    action {
                        sendEvent { ForecastEvent.Error(cityName) }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                action {
                    sendEvent { ForecastEvent.Error(cityName) }
                }
            }
        })

    }

    fun getDailyForecast(lat: Double?, long: Double?) {
        if (lat != null && long != null) {
            apiHelper.getDailyForecast(lat, long).enqueue(object :
                Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val moshi: Moshi =
                                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                            val adapter: JsonAdapter<ForecastDailyInfo>? =
                                moshi.adapter(ForecastDailyInfo::class.java)
                            val jsonString = response.body()
                            jsonString?.string()?.let {
                                val fromJson = adapter?.fromJson(it)
                                if (fromJson != null) {
                                    action {
                                        sendEvent { ForecastEvent.DailyInfoFound(fromJson) }
                                    }
                                }
                            }
                        } else {
                            if (response.code() == 404) {
                                action {
                                    sendEvent { ForecastEvent.Error(null) }
                                }
                            }
                        }

                    } catch (c: java.lang.Exception) {
                        action {
                            sendEvent { ForecastEvent.Error(null) }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    action {
                        sendEvent { ForecastEvent.Error(null) }
                    }
                }

            })
        }
    }
}
