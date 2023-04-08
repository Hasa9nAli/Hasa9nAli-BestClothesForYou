package com.hasan.bestclothesforyou.data

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("request") val request: Request,
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current
)