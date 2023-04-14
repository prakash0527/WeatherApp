package com.aligntech.weathermobileapp.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aligntech.weathermobileapp.R
import com.aligntech.weathermobileapp.databinding.FragmentTodayBinding
import com.aligntech.weathermobileapp.domain.model.TodayWeatherDetails
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*


class WeatherDetailsBottomSheetDialogFragment(val todayWeatherData : TodayWeatherDetails) : BottomSheetDialogFragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setWeatherData(todayWeatherData)
        return root
    }

    private fun setWeatherData(todayWeatherData : TodayWeatherDetails){
        binding.textViewLocationName.text =todayWeatherData.name
        binding.textViewDateTime.text = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
            Date((todayWeatherData.dt).toLong()*1000)
        )
        binding.textViewStatus.text = todayWeatherData.weather[0].main
        binding.textViewTemp.text = "${todayWeatherData.main.temp}°C"
        binding.textViewMinTem.text = "MinTemp: ${todayWeatherData.main.temp_min}°C"
        binding.textViewMixTemp.text = "MaxTemp: ${todayWeatherData.main.temp_max}°C"
        binding.textViewSunRise.text = getString(R.string.sunrise)+" : "+ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
            Date((todayWeatherData.sys.sunrise).toLong()*1000)
        )
        binding.textViewSunSet.text = getString(R.string.sunset)+" : "+ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
            Date((todayWeatherData.sys.sunset).toLong()*1000)
        )
        binding.textViewPressure.text = getString(R.string.pressure)+" : ${todayWeatherData.main.pressure}"
        binding.textViewHumidity.text = getString(R.string.humidity)+" : ${todayWeatherData.main.humidity}"
        binding.textViewWindSpeed.text = getString(R.string.wind_speed)+" : ${todayWeatherData.wind.speed} °"
    }

}
