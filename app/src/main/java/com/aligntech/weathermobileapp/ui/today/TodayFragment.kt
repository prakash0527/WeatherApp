package com.aligntech.weathermobileapp.ui.today
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.aligntech.weathermobileapp.R
import com.aligntech.weathermobileapp.common.Constants
import com.aligntech.weathermobileapp.common.NetworkUtils
import com.aligntech.weathermobileapp.databinding.FragmentTodayBinding
import com.aligntech.weathermobileapp.presentaion.today_weathr_details.TodayWeatherDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null
    private val viewModel: TodayWeatherDetailsViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (NetworkUtils.isInternetConnected(requireContext())) {
            viewModel.getTodayWeather(17.387140, 78.491684, Constants.API_KEY)
        }else{
            Toast.makeText(requireContext(),getString(R.string.please_check_your_internet_connection),Toast.LENGTH_LONG).show()
        }
        viewModel.state.observe(viewLifecycleOwner, Observer {
            if (it.isLoading){
                binding.progress.isVisible = true

            }else if (it.todayWeatherData?.id != null){
                binding.textViewLocationName.text = it.todayWeatherData.name
                binding.textViewDateTime.text = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date((it.todayWeatherData.dt).toLong()*1000)
                )
                binding.textViewStatus.text = it.todayWeatherData.weather[0].main
                binding.textViewTemp.text = "${it.todayWeatherData.main.temp}°C"
                binding.textViewMinTem.text = "MinTemp: ${it.todayWeatherData.main.temp_min}°C"
                binding.textViewMixTemp.text = "MaxTemp: ${it.todayWeatherData.main.temp_max}°C"
                binding.textViewSunRise.text = getString(R.string.sunrise)+" : "+ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                    Date((it.todayWeatherData.sys.sunrise).toLong()*1000)
                )
                binding.textViewSunSet.text = getString(R.string.sunset)+" : "+ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                    Date((it.todayWeatherData.sys.sunset).toLong()*1000)
                )
                binding.textViewPressure.text = getString(R.string.pressure)+" : ${it.todayWeatherData.main.pressure}"
                binding.textViewHumidity.text = getString(R.string.humidity)+" : ${it.todayWeatherData.main.humidity}"
                binding.textViewWindSpeed.text = getString(R.string.wind_speed)+" : ${it.todayWeatherData.wind.speed} °"

                binding.progress.isVisible = false
            }
            else{
                binding.progress.isVisible = false
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}