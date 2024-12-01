package com.example.moviebooking2

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivityEx : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedlocation:FusedLocationProviderClient
    private lateinit var editlocation: EditText
    private lateinit var text1: TextView
    private lateinit var text2: TextView

    private  var lattitudee:Double=0.0
    private  var longitudee:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_ex)
        fusedlocation= LocationServices.getFusedLocationProviderClient(this)
        val mapfragment=supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapfragment.getMapAsync(this)
    }

    override fun onMapReady(googlemap: GoogleMap) {
        text1=findViewById(R.id.textView)
        text2=findViewById(R.id.textView2)
        var btn: Button =findViewById(R.id.button)
        btn.setOnClickListener {

            googlemap.clear()
            editlocation=findViewById(R.id.editTextText)
            getLocationFromAddress(editlocation.text.toString())
            googleMap=googlemap

            val latLng = LatLng(lattitudee,longitudee)
            googlemap.addMarker(
                MarkerOptions()
                    .position(latLng).title("Marker location")        )
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 8f)
            googlemap.moveCamera(cameraUpdate)
        }
    }

    private fun getLocationFromAddress(location:String){
        val geocode= Geocoder(this)
        val list:MutableList<Address>?=geocode.getFromLocationName(location,5)
        if(list.isNullOrEmpty()){
            return
        }
        lattitudee=list[0].latitude
        longitudee=list[0].longitude

        text1.text="${lattitudee}"
        text2.text="${longitudee}"

    }
}