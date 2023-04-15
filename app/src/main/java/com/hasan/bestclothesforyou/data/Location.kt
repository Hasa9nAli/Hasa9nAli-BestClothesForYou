package com.hasan.bestclothesforyou.data

import com.google.gson.annotations.SerializedName

data class Location(
    val name: String,
    val country: String,
    val region: String,
    val lat: Double,
    val lon: Double,
    @SerializedName("timezone_id") val timezoneId: String,
    @SerializedName("localtime") val localtime: String,
)