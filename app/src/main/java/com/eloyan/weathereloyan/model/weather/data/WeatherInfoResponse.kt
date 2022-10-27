package com.eloyan.weathereloyan.model.weather.data

import com.google.gson.annotations.SerializedName

data class WeatherInfoResponse(
    @SerializedName("location")
    val location: Location = Location(),
    @SerializedName("current")
    val current: Current = Current(),
    @SerializedName("forecast")
    val forecast: Forecast = Forecast(),
)
