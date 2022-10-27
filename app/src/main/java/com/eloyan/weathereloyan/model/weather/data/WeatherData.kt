package com.eloyan.weathereloyan.model.weather.data

data class WeatherData(
    val city: String,
    val date: String,
    val conditionText: String,
    val conditionImageUrl: String,
    val maxTemperature: String,
    val currentTemperature: String,
    val minTemperature: String,
)
