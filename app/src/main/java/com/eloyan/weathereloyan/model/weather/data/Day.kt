package com.eloyan.weathereloyan.model.weather.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Day(
    @SerializedName("maxtemp_c")
    val maxTemperature: String = "",
    @SerializedName("mintemp_c")
    val minTemperature: String = "",
    @SerializedName("condition")
    val condition: Condition = Condition(),
) : Serializable
