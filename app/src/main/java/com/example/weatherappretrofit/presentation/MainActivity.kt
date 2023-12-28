package com.example.weatherappretrofit.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappretrofit.domain.WeatherInfo
import com.example.weatherappretrofit.screens.AppCard
import com.example.weatherappretrofit.screens.AppTabLayout
import com.example.weatherappretrofit.ui.theme.WeatherAppRetrofitTheme
import org.json.JSONObject

const val API_KEY = "5c3bf12832244738a48160550232712"

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppRetrofitTheme {
                val navController = rememberNavController()

                val daysList = remember {
                    mutableStateOf(listOf<WeatherInfo>())
                }
                val currentDay = remember {
                    mutableStateOf(
                        WeatherInfo(
                            "",
                            "",
                            "5.0",
                            "",
                            "",
                            "5.0",
                            "5.0",
                            ""
                        )
                    )
                }
                getDate("London", this, daysList, currentDay)

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        Scaffold(
                            topBar = {
                                AppBar(title = "London", navController = navController)
                            },
                            content = {
                                Column {
                                    AppCard(currentDay)
                                    AppTabLayout(daysList, currentDay)
                                }
                            },
                            bottomBar = {
                                BottomBar(navController = navController)
                            }
                        )
                    }
                    composable("settings") {
                        SettingsScreen(navController = navController)
                    }
                    composable("calendar") {
                        CalendarScreen(navController = navController)
                    }
                    composable("search") {
                        SearchScreen(navController = navController)
                    }
                }
            }
        }
    }
}

private fun getDate(
    city: String, context: Context,
    daysList: MutableState<List<WeatherInfo>>,
    currentDay: MutableState<WeatherInfo>
) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
            "$API_KEY&q=${city}&days=3&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            Log.d("MyLog", "Response: $it")
            val list = getDataDays(it)
            currentDay.value = list[0]
            daysList.value = list

        }, {
            Log.d("MyLog", "VolleyError: $it")
        }
    )
    queue.add(sRequest)
}

private fun getDataDays(response: String): List<WeatherInfo> {
    if (response.isEmpty()) return listOf()
    val list = ArrayList<WeatherInfo>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")

    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        list.add(
            WeatherInfo(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
                item.getJSONArray("hour").toString()
            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c")
    )
    return list
}



