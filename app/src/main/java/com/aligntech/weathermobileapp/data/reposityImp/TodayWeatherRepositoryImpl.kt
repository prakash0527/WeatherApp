package com.aligntech.weathermobileapp.data.reposityImp

import com.aligntech.weathermobileapp.data.remote.WeatherApi
import com.aligntech.weathermobileapp.data.remote.dto.TodayWeatherDto
import com.aligntech.weathermobileapp.domain.repository.TodayRepository
import javax.inject.Inject

class TodayWeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : TodayRepository {
    override suspend fun getTodayWeather(lat: Double, lon: Double, key: String): TodayWeatherDto {
        return api.getTodayWeather(lat,lon,key)
    }

}