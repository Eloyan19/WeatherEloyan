package com.eloyan.weathereloyan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eloyan.weathereloyan.model.common.RequestCompleteListener
import com.eloyan.weathereloyan.model.common.RequestResult
import com.eloyan.weathereloyan.model.location.LocationError
import com.eloyan.weathereloyan.model.location.LocationRepository
import com.eloyan.weathereloyan.model.location.data.Coordinates
import com.eloyan.weathereloyan.model.weather.WeatherRepository
import com.eloyan.weathereloyan.model.weather.data.WeatherData
import com.eloyan.weathereloyan.model.weather.data.WeatherInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    object VMConstants {
        const val WEATHER_FORECAST_DAYS = 7
    }

    val currentWeatherLiveData = MutableLiveData<WeatherData>()
    val weekWeatherLiveData = MutableLiveData<List<WeatherData>>()
    val dailyWeatherLiveData = MutableLiveData<List<WeatherData>>()
    val locationCoordinates = MutableLiveData<RequestResult<Coordinates, LocationError>>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    fun getWeatherInfo(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            progressBarLiveData.postValue(true)

            weatherRepository.getWeatherInfo(city, VMConstants.WEATHER_FORECAST_DAYS,
                object : RequestCompleteListener<WeatherInfoResponse, String> {
                    override fun onRequestSuccess(data: WeatherInfoResponse) {
                        setCurrentWeatherInfo(data)
                        setWeekWeatherInfo(data)
                        setDailyWeatherData(data)

                        progressBarLiveData.postValue(false)
                    }

                    override fun onRequestFailed(errorMessage: String) {
                        progressBarLiveData.postValue(false)
                    }
                })
        }
    }

    fun getLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.locationRequest(object :
                RequestCompleteListener<Coordinates, LocationError> {
                override fun onRequestSuccess(data: Coordinates) {
                    locationCoordinates.postValue(RequestResult.Success(data))
                }

                override fun onRequestFailed(locationError: LocationError) {
                    locationCoordinates.postValue(RequestResult.Error(locationError))
                }
            })
        }
    }

    private fun setCurrentWeatherInfo(data: WeatherInfoResponse) {
        val currentWeather = WeatherData(
            "${data.location.name} / ${data.location.region}",
            data.location.date,
            data.current.condition.conditionText,
            data.current.condition.conditionImageUrl,
            data.forecast.dailyWeatherList.first().day.maxTemperature,
            data.current.currentTemperature,
            data.forecast.dailyWeatherList.first().day.minTemperature,
        )

        currentWeatherLiveData.postValue(currentWeather)
    }

    private fun setDailyWeatherData(data: WeatherInfoResponse) {
        val dailyWeatherList = mutableListOf<WeatherData>()
        val hoursWeather = data.forecast.dailyWeatherList.first().dailyWeather

        for (hourWeather in hoursWeather) {
            dailyWeatherList.add(
                WeatherData(
                    "",
                    hourWeather.date,
                    hourWeather.condition.conditionText,
                    hourWeather.condition.conditionImageUrl,
                    "",
                    hourWeather.currentTemperature,
                    ""
                )
            )
        }
        dailyWeatherLiveData.postValue(dailyWeatherList)
    }

    private fun setWeekWeatherInfo(data: WeatherInfoResponse) {
        val forecast = data.forecast.dailyWeatherList
        val weekWeatherList = mutableListOf<WeatherData>()
        for (forecastDay in forecast) {
            weekWeatherList.add(
                WeatherData(
                    "",
                    forecastDay.date,
                    forecastDay.day.condition.conditionText,
                    forecastDay.day.condition.conditionImageUrl,
                    forecastDay.day.maxTemperature,
                    "",
                    forecastDay.day.minTemperature
                )
            )
        }

        weekWeatherLiveData.postValue(weekWeatherList)
    }
}