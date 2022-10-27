package com.eloyan.weathereloyan.model.weather.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Forecast(
    @SerializedName("forecastday")
    val dailyWeatherList: List<ForecastDay> = listOf(),
) : Serializable
