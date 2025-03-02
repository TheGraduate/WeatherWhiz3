package com.example.weatherwhiz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?exclude=minutely,hourly,daily,alerts&units=metric&appid=${NetworkService.WEATHER_API_KEY}")
    fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherResponse>
}