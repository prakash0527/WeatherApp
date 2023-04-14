package com.aligntech.weathermobileapp.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aligntech.weathermobileapp.AppApplication
import com.aligntech.weathermobileapp.R
import com.aligntech.weathermobileapp.databinding.FragmentHomeBinding
import com.aligntech.weathermobileapp.domain.model.WeatherLocation
import com.aligntech.weathermobileapp.presentaion.today_weathr_details.TodayWeatherDetailsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), GoogleMap.OnMapClickListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val currentLocation = LatLng(17.387140,78.491684)
        googleMap.addMarker(MarkerOptions().position(currentLocation).title("Current location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))

        // Set the zoom level to 15
        val zoomLevel = 15f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel))
        map = googleMap

        googleMap.setOnMarkerClickListener { marker ->

            false
        }
        googleMap.setOnMapClickListener(this)
    }
    private val binding get() = _binding!!
    private val viewModel: TodayWeatherDetailsViewModel by viewModels()
    private var mapFragment : SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        getCurrentLocation()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun getCurrentLocation(){

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        } else {
           val task = fusedLocationClient.lastLocation
            task.addOnSuccessListener { location ->
                mapFragment?.getMapAsync { googleMap ->
                    if (location != null) {
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        googleMap.addMarker(
                            MarkerOptions().position(currentLocation).title("Current location")
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                    }

                }

            }


        }

    }

    override fun onMapClick(location: LatLng) {
        val markerOptions = MarkerOptions().position(location).title("")
        map.addMarker(markerOptions)
        // saving data temporarily
        AppApplication.weatherLocations.add(WeatherLocation("Location",location.latitude,location.longitude))

    }
}