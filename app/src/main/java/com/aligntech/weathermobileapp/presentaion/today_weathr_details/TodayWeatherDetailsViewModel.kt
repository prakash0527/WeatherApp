package com.aligntech.weathermobileapp.presentaion.today_weathr_details

import androidx.lifecycle.*
import com.aligntech.weathermobileapp.common.Resource
import com.aligntech.weathermobileapp.domain.model.WeatherLocation
import com.aligntech.weathermobileapp.domain.use_case.get_today_weather.GetTodayWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TodayWeatherDetailsViewModel @Inject constructor(
    private val getTodayWeatherUseCase: GetTodayWeatherUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData(TodayWeatherDetailState())
    val state: LiveData<TodayWeatherDetailState> = _state



    fun getTodayWeather(lat: Double,lon : Double, apiKey :  String) {
        getTodayWeatherUseCase.invoke(lat,lon,apiKey).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = TodayWeatherDetailState(todayWeatherData = result.data)
                }
                is Resource.Error -> {
                    _state.value = TodayWeatherDetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = TodayWeatherDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}