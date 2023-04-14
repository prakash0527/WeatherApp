package com.aligntech.weathermobileapp.data.remote.dto

import com.aligntech.weathermobileapp.domain.model.TodayWeatherDetails

data class TodayWeatherDto(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)


fun TodayWeatherDto.toWeatherDetail(): TodayWeatherDetails {
    return TodayWeatherDetails(
        base = base,
        clouds = clouds,
        cod = cod,
        coord = coord,
        id = id,
        main = main,
        name = name,
        sys = sys,
        timezone = timezone, visibility = visibility,
        wind = wind,
        weather = weather,
        dt = dt
    )
}