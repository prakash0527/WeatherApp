package com.aligntech.weathermobileapp.domain.repository

import com.aligntech.weathermobileapp.data.remote.dto.TodayWeatherDto

interface TodayRepository {

    suspend fun getTodayWeather(lat : Double, lon : Double, key : String): TodayWeatherDto
}