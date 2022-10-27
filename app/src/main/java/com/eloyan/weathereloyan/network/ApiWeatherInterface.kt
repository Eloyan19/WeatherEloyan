package com.eloyan.weathereloyan.network
import com.eloyan.weathereloyan.model.weather.data.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeatherInterface {
    @GET("forecast.json")
    fun callApiForWeatherInfo(@Query("q") city: String, @Query("days") days: Int): Call<WeatherInfoResponse>
}