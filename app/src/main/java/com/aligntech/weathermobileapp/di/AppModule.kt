package com.aligntech.weathermobileapp.di

import com.aligntech.weathermobileapp.common.Constants
import com.aligntech.weathermobileapp.data.remote.WeatherApi
import com.aligntech.weathermobileapp.data.reposityImp.TodayWeatherRepositoryImpl
import com.aligntech.weathermobileapp.domain.repository.TodayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTodayWeatherRepository(api: WeatherApi): TodayRepository {
        return TodayWeatherRepositoryImpl(api)
    }
}