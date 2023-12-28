package com.example.weatherappretrofit.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SearchScreen(navController: NavController) {
    LazyColumn {
        items(10) { index ->
            QuestionCard(question = "Вопрос $index?")
        }
    }
}

@Composable
fun QuestionCard(question: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = question,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text("Дополнительная информация о вопросе может быть здесь.")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Подробнее",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                }
            )
        }
    }
}