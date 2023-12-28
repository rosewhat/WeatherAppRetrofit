package com.example.weatherappretrofit.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalTime

@Composable
fun CalendarScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val currentTime = remember {
            mutableStateOf(LocalTime.now())
        }
        Text(
            text = "Текущее время: ${currentTime.value}",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        WarningSection()
        WeatherSection()
    }
}

@Composable
fun WarningSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Предупреждения:", fontWeight = FontWeight.Bold)
        // Здесь можно добавить список предупреждений из вашей модели данных
        Text("1. Пожароопасность высока")
        Text("2. Ветер: 20 м/с")
    }
}

@Composable
fun WeatherSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Предполагаемая погода:", fontWeight = FontWeight.Bold)
        Text("Температура: 25°C")
        Text("Влажность: 60%")
    }
}