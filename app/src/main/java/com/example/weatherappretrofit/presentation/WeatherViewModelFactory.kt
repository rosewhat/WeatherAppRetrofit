package com.example.weatherappretrofit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappretrofit.domain.WeatherRepository

class WeatherViewModelFactory(private val weatherRepository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}