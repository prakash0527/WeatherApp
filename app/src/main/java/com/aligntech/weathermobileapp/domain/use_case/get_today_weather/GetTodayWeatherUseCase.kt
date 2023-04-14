package com.aligntech.weathermobileapp.domain.use_case.get_today_weather

import com.aligntech.weathermobileapp.common.Resource
import com.aligntech.weathermobileapp.data.remote.dto.toWeatherDetail
import com.aligntech.weathermobileapp.domain.model.TodayWeatherDetails
import com.aligntech.weathermobileapp.domain.repository.TodayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTodayWeatherUseCase @Inject constructor(
    private val repository: TodayRepository
) {
    operator fun invoke(lat: Double,lon : Double, apiKey : String ): Flow<Resource<TodayWeatherDetails>> = flow {
        try {
            emit(Resource.Loading<TodayWeatherDetails>())
            val weather = repository.getTodayWeather(lat, lon, apiKey).toWeatherDetail()
            emit(Resource.Success<TodayWeatherDetails>(weather))
        } catch(e: HttpException) {
            emit(Resource.Error<TodayWeatherDetails>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<TodayWeatherDetails>("Couldn't reach server. Check your internet connection."))
        }
    }
}