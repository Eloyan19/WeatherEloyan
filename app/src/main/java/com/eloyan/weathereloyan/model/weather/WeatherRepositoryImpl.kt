package com.eloyan.weathereloyan.model.weather

import com.eloyan.weathereloyan.model.common.RequestCompleteListener
import com.eloyan.weathereloyan.model.weather.data.WeatherInfoResponse
import com.eloyan.weathereloyan.network.ApiWeatherInterface
import com.eloyan.weathereloyan.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherRepository {
    fun getWeatherInfo(
        city: String,
        days: Int,
        callback: RequestCompleteListener<WeatherInfoResponse, String>
    )
}

@Singleton
class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {
    override fun getWeatherInfo(
        city: String,
        days: Int,
        callback: RequestCompleteListener<WeatherInfoResponse, String>
    ) {
        val apiInterface = RetrofitClient.client.create(ApiWeatherInterface::class.java)
        val call: Call<WeatherInfoResponse> = apiInterface.callApiForWeatherInfo(city, days)

        call.enqueue(object : Callback<WeatherInfoResponse> {
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null) {
                    callback.onRequestSuccess(response.body()!!)
                } else {
                    callback.onRequestFailed(response.message())
                }
            }

            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage)
            }
        })
    }
}