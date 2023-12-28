package com.example.weatherappretrofit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappretrofit.R
import com.example.weatherappretrofit.domain.WeatherInfo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun MainList(list: List<WeatherInfo>, currentDay: MutableState<WeatherInfo>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            list
        ) { _, item ->
            AppList(item, currentDay)
        }
    }
}

@Composable
fun AppCard(currentDay: MutableState<WeatherInfo>) {
    Column(
        modifier = Modifier.padding(5.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 18.dp, start = 8.dp),
                        text = currentDay.value.time,
                        style = TextStyle(fontSize = 15.sp)
                    )
                    AsyncImage(
                        model = "https://" + currentDay.value.icon,
                        contentDescription = "image",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(top = 3.dp, end = 8.dp)
                    )
                }
                Text(
                    text = currentDay.value.city, style = TextStyle(fontSize = 15.sp)
                )
                Text(
                    text = if(currentDay.value.currentTemp.isNotEmpty())
                        currentDay.value.currentTemp.toFloat().toInt().toString() + "°C"
                    else "${currentDay.value.maxTemp.toFloat().toInt().toString()} °C/${currentDay.value.minTemp.toFloat()} °C",

                    style = TextStyle(fontSize = 65.sp)
                )
                Text(
                    text = currentDay.value.condition, style = TextStyle(fontSize = 16.sp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "image"
                        )
                    }
                    Text(
                        text = "${
                            currentDay.value.maxTemp.toFloat().toInt()
                        } C/${currentDay.value.minTemp.toFloat().toInt()}",
                        style = TextStyle(fontSize = 16.sp)
                    )

                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "image"
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppTabLayout(daysList: MutableState<List<WeatherInfo>>, currentDay: MutableState<WeatherInfo>) {
    val tabList = listOf("Время", "Дни")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(start = 3.dp, end = 3.dp)
    ) {
        TabRow(
            selectedTabIndex = tabIndex, indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, it)
                )
            }, modifier = Modifier.background(Color.Blue)
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(selected = false, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            index
                        )
                    }
                }, text = {
                    Text(text = text)
                })
            }
        }
        HorizontalPager(
            count = tabList.size, state = pagerState, modifier = Modifier.weight(1.0f)
        ) { index ->
            val list = when (index) {
                0 -> getWeatherHours(currentDay.value.hours)
                1 -> daysList.value
                else -> daysList.value
            }
            MainList(list, currentDay)
        }
    }
}

private fun getWeatherHours(hours: String): List<WeatherInfo> {
    if (hours.isEmpty()) return listOf()
    val hoursArray = JSONArray(hours)
    val list = ArrayList<WeatherInfo>()
    for (i in 0 until hoursArray.length()) {
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherInfo(
                "",
                item.getString("time"),
                item.getString("temp_c").toFloat().toInt().toString() + "°C",
                item.getJSONObject("condition").getString("text"),
                item.getJSONObject("condition").getString("icon"),
                "",
                "",
                ""
            )
        )
    }
    return list
}


@Composable
fun AppList(item: WeatherInfo, currentDay: MutableState<WeatherInfo>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                currentDay.value = item
            }, shape = RoundedCornerShape(5.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 8.dp, top = 5.dp, bottom = 5.dp
                )
            ) {
                Text(text = item.time)
                Text(
                    text = item.condition
                )
            }
            Text(
                text = item.currentTemp.ifEmpty {
                    "${item.maxTemp}/${item.minTemp}"
                },
                style = TextStyle(fontSize = 25.sp)
            )
            AsyncImage(
                model = "https:${item.icon}",
                contentDescription = "image",
                modifier = Modifier
                    .size(35.dp)
                    .padding(top = 8.dp)
                    .size(35.dp)
            )
        }
    }
}
