package com.example.weatherappretrofit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherappretrofit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.background(color = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {  }) {
                Icon(
                    painterResource(id = R.drawable.home),
                    contentDescription = "Home", tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = {
                navController.navigate("share")
            }) {
                Icon(
                    painterResource(id = R.drawable.share),
                    contentDescription = "Like", tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = {
                navController.navigate("calendar")
            }) {
                Icon(
                    painterResource(id = R.drawable.calendar),
                    contentDescription = "Like", tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = {
                navController.navigate("settings")
            }) {
                Icon(
                    painterResource(id = R.drawable.settings),
                    contentDescription = "Settings", tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}