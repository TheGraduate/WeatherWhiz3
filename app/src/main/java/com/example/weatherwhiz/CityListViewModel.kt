package com.example.weatherwhiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherwhiz.City
import com.example.weatherwhiz.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityListViewModel : ViewModel() {

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> get() = _cities

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    init {
        fetchCities()
    }

    fun fetchCities() {
        _isLoading.value = true
        _error.value = false

        val call = NetworkService.cityApi.getCities()
        call.enqueue(object : Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _cities.value = response.body()
                } else {
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
            }
        })
    }
}