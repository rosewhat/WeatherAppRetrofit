package com.example.weatherappretrofit.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappretrofit.domain.WeatherInfo
import com.example.weatherappretrofit.domain.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val currentDay = MutableLiveData<WeatherInfo>()

    val daysList = MutableLiveData<List<WeatherInfo>>()

    fun getWeatherInfo(city: String) {
        viewModelScope.launch {
            try {
                val weatherInfoList = repository.getWeatherInfo(city)
                currentDay.value = weatherInfoList.firstOrNull()
                daysList.value = weatherInfoList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}