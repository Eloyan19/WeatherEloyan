package com.eloyan.weathereloyan.model.weather.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Condition(
    @SerializedName("text")
    val conditionText: String = "",
    @SerializedName("icon")
    val conditionImageUrl: String = "",
) : Serializable
