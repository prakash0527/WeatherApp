package com.aligntech.weathermobileapp.ui.city.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aligntech.weathermobileapp.databinding.ItemLocationBinding
import com.aligntech.weathermobileapp.domain.model.WeatherLocation
import com.aligntech.weathermobileapp.ui.city.OnItemLocationsClickListener


class LocationsAdapter(
    private val listener: OnItemLocationsClickListener,
    private val locationList: MutableList<WeatherLocation>
) : RecyclerView.Adapter<LocationsAdapter.LocationViewHolder>() {

    private lateinit var binding: ItemLocationBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val list = locationList[position]
        holder.bind(list,position)
        binding.textViewWeatherLocation.setOnClickListener {
            listener.onItemClick(position,locationList[position])
        }
    }

    override fun getItemCount(): Int = locationList.size

    class LocationViewHolder(private val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: WeatherLocation, pos : Int) {
            binding.textViewWeatherLocation.text = location.locationName+" ${pos.plus(1)}"

        }
    }

}

