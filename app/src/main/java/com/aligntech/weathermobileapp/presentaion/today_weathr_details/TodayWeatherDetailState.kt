package com.aligntech.weathermobileapp.presentaion.today_weathr_details

import com.aligntech.weathermobileapp.domain.model.TodayWeatherDetails

data class TodayWeatherDetailState(
    val isLoading: Boolean = false,
    val todayWeatherData: TodayWeatherDetails? = null,
    val error: String = ""
)
