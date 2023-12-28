package com.example.weatherappretrofit.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherappretrofit.R

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Настройки",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ProfileSection()

        SettingsItem("Избранное")
        SettingsItem("Уведомления")
        SettingsItem("Конфиденциальность")
        SettingsItem("Данные и память")
        SettingsItem("Оформление")
        SettingsItem("Язык")
        SettingsItem("Помощь")
        SettingsItem("Вопросы")

        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Выйти из аккаунта")
        }
    }
}

@Composable
fun ProfileSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text("Имя пользователя", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Text("andzhaev@mirea.ru")
    }
}

@Composable
fun SettingsItem(option: String) {
    Text(
        text = option,
        style = TextStyle(fontSize = 18.sp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {

            }
    )
}