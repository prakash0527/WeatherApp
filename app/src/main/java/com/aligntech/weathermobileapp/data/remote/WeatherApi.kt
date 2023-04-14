package com.aligntech.weathermobileapp.data.remote

import com.aligntech.weathermobileapp.data.remote.dto.TodayWeatherDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getTodayWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units : String = "metric"
    ): TodayWeatherDto
}
