package com.hasan.bestclothesforyou.data

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("observation_time") val observationTime: String,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("weather_icons") val weatherIcons: List<String>,
    @SerializedName("weather_descriptions") val weatherDescriptions: List<String>,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_degree") val windDegree: Double,
    @SerializedName("cloudcover") val cloudCover: Double,
    val temperature: Double,
    val humidity: Double,
    val pressure: Double,
)