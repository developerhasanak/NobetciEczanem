package com.hasan.nobetcieczanem.view

import android.app.Service
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
import androidx.fragment.app.FragmentManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.hasan.nobetcieczanem.R
import java.security.Provider
import java.util.jar.Manifest

class MapsFragment : Fragment(){

   private var loc:String?=null
   private var name:String?=null





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
      arguments?.let {
            loc = MapsFragmentArgs.fromBundle(it).loc
            name = MapsFragmentArgs.fromBundle(it).name
       }


    }


    private val callback = OnMapReadyCallback { googleMap ->


        loc?.let {
           val local= loc!!.split(",")

            val sydney = LatLng(local[0].toDouble(),local[1].toDouble())
            googleMap.addMarker(MarkerOptions().position(sydney).title(name))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12f))
        }


    }




}