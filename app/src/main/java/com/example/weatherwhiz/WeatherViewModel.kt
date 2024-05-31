package com.example.weatherwhiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherwhiz.NetworkService
import com.example.weatherwhiz.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> get() = _weather

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun fetchWeather(lat: Double, lon: Double) {
        _isLoading.value = true
        _error.value = false

        val call = NetworkService.weatherApi.getWeather(lat, lon)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _weather.value = response.body()
                } else {
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
            }
        })
    }
}