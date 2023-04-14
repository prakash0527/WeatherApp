package com.aligntech.weathermobileapp

import android.app.Application
import com.aligntech.weathermobileapp.domain.model.WeatherLocation
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application(){

    companion object{
        val weatherLocations = mutableListOf<WeatherLocation>()
    }
}