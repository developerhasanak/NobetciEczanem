package com.hasan.nobetcieczanem.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.hasan.nobetcieczanem.R

class MapsFragment2 : Fragment() {

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {

        registerLauncher()
        super.onCreate(savedInstanceState)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        context?.let {
            locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        }
        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                val konum = LatLng(p0.latitude,p0.longitude)
                //googleMap.addMarker(MarkerOptions().position(konum).title("Buradasınız"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f))
            }

        }

        if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)){
                Snackbar.make(requireView(),"Konum izni vermelisin", Snackbar.LENGTH_INDEFINITE).setAction("izin ver"){
                    //izin alınacak
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }.show()

            }else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

                //izin alınacak
            }
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
            val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if(lastLocation !=null){
                val konum = LatLng(lastLocation.latitude,lastLocation.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f))
            }

            googleMap.isMyLocationEnabled = true


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun registerLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
                    val  lastlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (lastlocation != null) {
                        val konum = LatLng(lastlocation.latitude,lastlocation.longitude)
                        //mMap.addMarker(MarkerOptions().position(konum).title("konum"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f))
                    }
                    mMap.isMyLocationEnabled= true
                }

            }

        }

    }
}