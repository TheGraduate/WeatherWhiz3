package com.example.weatherwhiz

data class WeatherResponse(
    val main: Main,
    val name: String
)

data class Main(
    val temp: Double
)