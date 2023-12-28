package com.example.weatherappretrofit.domain

interface WeatherRepository {
    suspend fun getWeatherInfo(city: String): List<WeatherInfo>
}