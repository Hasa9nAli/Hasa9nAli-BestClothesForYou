package com.hasan.bestclothesforyou.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.net.URL

data class Current(
    @SerializedName("observation_time") val observationTime: String,
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("weather_icons") val weatherIcons: List<String>,
    @SerializedName("weather_descriptions") val weatherDescriptions: List<String>,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_degree") val windDegree: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("cloudcover") val cloudCover: Double,
)