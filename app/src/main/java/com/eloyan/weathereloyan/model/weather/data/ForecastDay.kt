package com.eloyan.weathereloyan.model.weather.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastDay(
    @SerializedName("date")
    val date: String = "",
    @SerializedName("day")
    val day: Day = Day(),
    @SerializedName("hour")
    val dailyWeather: List<Current> = listOf(),
) : Serializable