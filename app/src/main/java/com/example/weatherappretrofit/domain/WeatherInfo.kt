package com.example.weatherappretrofit.domain

data class WeatherInfo(
    val city: String,
    val time: String,
    val currentTemp: String,
    val condition: String,
    val icon: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String
)