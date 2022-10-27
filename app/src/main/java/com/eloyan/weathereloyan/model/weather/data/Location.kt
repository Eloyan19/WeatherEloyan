package com.eloyan.weathereloyan.model.weather.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("region")
    val region: String = "",
    @SerializedName("localtime")
    val date: String = ""
) : Serializable
