package com.hasan.bestclothesforyou.data


data class WeatherData(
    val request: Request,
    val location: Location,
    val current: Current
)