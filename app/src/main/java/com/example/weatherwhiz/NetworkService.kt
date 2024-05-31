package com.example.weatherwhiz

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private const val BASE_URL = "https://api.openweathermap.org/"
    private const val CITY_URL = "https://gist.githubusercontent.com/"
    const val WEATHER_API_KEY = "671006722ee1aae5cf04a0d0e2de2aef"

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    val cityApi: CityApi by lazy {
        Retrofit.Builder()
            .baseUrl(CITY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityApi::class.java)
    }
}