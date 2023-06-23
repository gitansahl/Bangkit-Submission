package com.gitan.mystoryapp.view.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.gitan.mystoryapp.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.gitan.mystoryapp.databinding.ActivityMapsBinding
import com.gitan.mystoryapp.view.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapsViewModel: MapsViewModel
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setUpViewModel()
    }

    private fun setUpViewModel() {
        mapsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this@MapsActivity)
        )[MapsViewModel::class.java]

        mapsViewModel.getStory()
        mapsViewModel.story.observe(this) {
            it.forEach { story ->
                val latLng = story.lat?.let { it1 -> story.lon?.let { it2 -> LatLng(it1, it2) } }
                latLng?.let { it1 ->
                    MarkerOptions().position(it1).title(story.name).snippet(story.description)
                }?.let { it2 ->
                    mMap.addMarker(it2)
                }
                latLng?.let { it1 -> boundsBuilder.include(it1) }
            }

            val bounds: LatLngBounds = boundsBuilder.build()
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    resources.displayMetrics.widthPixels,
                    resources.displayMetrics.heightPixels,
                    300
                )
            )
        }
    }
}