package com.example.pockemongame

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermmison()
        loadPockemon()
    }

    var accessLocation = 123

    fun checkPermmison() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), accessLocation)
                return
            }
        }
        getUserLocation()
    }

    fun getUserLocation() {
        Toast.makeText(this, "User location access on", Toast.LENGTH_SHORT).show()

        var myLocation = myLocationListener()
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3f, myLocation)
        var my_Thread = myThread()
        my_Thread.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            accessLocation -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation()
                } else {
                    Toast.makeText(this, "Cannot access your location", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

    }

    var location: Location? = null

    inner class myLocationListener: LocationListener {

        constructor() {
            location = Location("Start")
            location!!.longitude = 0.0
            location!!.latitude = 0.0
        }

        override fun onLocationChanged(p0: Location?) {
            location = p0
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onProviderEnabled(provider: String?) {
            TODO("Not yet implemented")
        }

        override fun onProviderDisabled(provider: String?) {
            TODO("Not yet implemented")
        }

    }

    var oldLocation: Location? = null
    inner class myThread: Thread {
        constructor(): super() {
            oldLocation = Location("Start")
            oldLocation!!.longitude = 0.0
            oldLocation!!.latitude = 0.0
        }

        override fun run() {
            while (true) {
                try {
                    if (oldLocation!!.distanceTo(location) == 0f) {
                        continue
                    }
                    oldLocation = location
                    runOnUiThread {
                        mMap.clear()
                    val sydney = LatLng(location!!.latitude, location!!.longitude)
                    mMap.addMarker(MarkerOptions()
                        .position(sydney)
                        .title("Me")
                        .snippet("Here is my location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f))

                        for (i in 0 until listPockemons.size) {
                            var newPockemon = listPockemons[i]
                            if (newPockemon.isCatch == false) {
                                var pockmonLocation = LatLng(newPockemon.location!!.latitude, newPockemon.location!!.longitude)
                                mMap.addMarker(MarkerOptions()
                                    .position(pockmonLocation)
                                    .title(newPockemon.name)
                                    .snippet(newPockemon.des + ", power: " + newPockemon.power)
                                    .icon(BitmapDescriptorFactory.fromResource(newPockemon.img!!)))
                                Log.d("pokemon", location!!.distanceTo(newPockemon.location).toString())
                                if (location!!.distanceTo(newPockemon.location) < 10) {
                                    newPockemon.isCatch = true
                                    listPockemons[i] = newPockemon
                                    playerPower += newPockemon.power!!.toDouble()
                                    Toast.makeText(applicationContext, "You gotcha " + newPockemon.name + " with power " + newPockemon.power, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    Thread.sleep(1000)
                } catch (ex: Exception) {

                }

            }
        }
    }

    var playerPower: Double = 0.0
    var listPockemons = ArrayList<Pockemon>()

    fun loadPockemon() {
        listPockemons.add(Pockemon(R.drawable.charmander, "Charmander", "salamandra de fogo", 55.0, -22.8583, -43.2667))
        listPockemons.add(Pockemon(R.drawable.squirtle, "Squirtle", "Tartaruga", 33.5, -22.8389, -43.2669))
        listPockemons.add(Pockemon(R.drawable.bulbasaur, "Bulbasaur", "planta", 70.0, -22.8480, -43.2560))
    }
}
