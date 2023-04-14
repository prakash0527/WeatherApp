package com.aligntech.weathermobileapp.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aligntech.weathermobileapp.AppApplication
import com.aligntech.weathermobileapp.R
import com.aligntech.weathermobileapp.common.Constants
import com.aligntech.weathermobileapp.common.NetworkUtils
import com.aligntech.weathermobileapp.databinding.FragmentLocationsBinding
import com.aligntech.weathermobileapp.domain.model.TodayWeatherDetails
import com.aligntech.weathermobileapp.domain.model.WeatherLocation
import com.aligntech.weathermobileapp.presentaion.today_weathr_details.TodayWeatherDetailsViewModel
import com.aligntech.weathermobileapp.ui.city.adapter.LocationsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : Fragment() , OnItemLocationsClickListener {

    private var _binding: FragmentLocationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: TodayWeatherDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerViewLocations = binding.recyclerViewLocations
         val adapter = LocationsAdapter(this,AppApplication.weatherLocations)
        recyclerViewLocations.adapter = adapter
        recyclerViewLocations.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        recyclerViewLocations.addItemDecoration(dividerItemDecoration)



        // Add swipe-to-delete functionality to the RecyclerView
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                // Not used in this
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Remove the item from the dataset
                val position = viewHolder.adapterPosition
                AppApplication.weatherLocations.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewLocations)
        binding.noBookMarks.isVisible = AppApplication.weatherLocations.size == 0
        viewModel.state.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isLoading){
             binding.progress.isVisible = true
            }else if(it.todayWeatherData?.id != null){
                showBottomSheetDialog(childFragmentManager,it.todayWeatherData)
                binding.progress.isVisible = false
            }else{
                binding.progress.isVisible = false
            }

        })





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int, weatherDataLocation: WeatherLocation) {

        if (NetworkUtils.isInternetConnected(requireContext())) {
            viewModel.getTodayWeather(weatherDataLocation.latitude,weatherDataLocation.longitude,Constants.API_KEY)

        }else{
            Toast.makeText(requireContext(),getString(R.string.please_check_your_internet_connection), Toast.LENGTH_LONG).show()
        }


    }
    private fun showBottomSheetDialog(childFragmentManager: FragmentManager,todayWeatherDetails : TodayWeatherDetails) {
        val bottomSheetDialogFragment = WeatherDetailsBottomSheetDialogFragment(todayWeatherDetails)
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)


    }

}
interface OnItemLocationsClickListener {
    fun onItemClick(position: Int, weatherDataLocation : WeatherLocation)

}

