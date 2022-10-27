package com.eloyan.weathereloyan.model.weather.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Current(
    @SerializedName("time")
    val date: String = "",
    @SerializedName("temp_c")
    val currentTemperature: String = "",
    @SerializedName("condition")
    val condition: Condition = Condition(),
) : Serializable
