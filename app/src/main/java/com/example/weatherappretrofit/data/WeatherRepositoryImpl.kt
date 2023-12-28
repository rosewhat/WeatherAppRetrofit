package com.example.weatherappretrofit.data

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappretrofit.domain.WeatherInfo
import com.example.weatherappretrofit.domain.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class WeatherRepositoryImpl(private val context: Context) : WeatherRepository {

    private val API_KEY = "5c3bf12832244738a48160550232712"

    override suspend fun getWeatherInfo(city: String): List<WeatherInfo> = withContext(Dispatchers.IO) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$city&days=3&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)

        return@withContext try {
            val response = getApiResponse(queue, url)
            parseResponse(response)
        } catch (e: Exception) {
            Log.e("MyLog", "Error fetching weather info", e)
            emptyList()
        }
    }

    private suspend fun getApiResponse(queue: RequestQueue, url: String): String {
        return suspendCoroutine { continuation ->
            val sRequest = StringRequest(
                Request.Method.GET,
                url,
                { response -> continuation.resume(response) },
                { error -> continuation.resumeWithException(error) }
            )
            queue.add(sRequest)
        }
    }

    fun parseResponse(response: String): List<WeatherInfo> {
        val list = mutableListOf<WeatherInfo>()
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
}