package com.example.getsunsetapp

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermmison()

        btnGetSunset.setOnClickListener {
            val lat: Double? = location!!.latitude
            val long: Double? = location!!.longitude
            val url =
                "https://api.met.no/weatherapi/sunrise/2.0/.json?lat=$lat&lon=$long&date=2020-05-05&offset=-03:00"
            MyAsyncTask().execute(url)
        }
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
                    Toast.makeText(this, "Pfv conceda a permissao de localização", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    var location: Location? = null

    inner class myLocationListener() : LocationListener {

        init {
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

    inner class MyAsyncTask: AsyncTask<String, String, String>() {

        override fun onPreExecute() {

        }

        override fun doInBackground(vararg p0: String?): String {
            try {
                val url = URL(p0[0])
                val urlConnect = url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout = 7000

                var inString = ConvertStreamToString(urlConnect.inputStream)
                publishProgress(inString)
            } catch (ex: Exception) {
                Toast.makeText(applicationContext, "Erro, tente novamente", Toast.LENGTH_SHORT ).show()
                Log.d("caralho", ex.toString())
            }
            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val json = JSONObject(values[0]!!)
                val location = json.getJSONObject("location")
                val time = location.getJSONArray("time")
                val obj = time.getJSONObject(0)
                val sunset = obj.getJSONObject("sunset")
                val sunsetTime = sunset.getString("time")
                val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("HH:mm")
                val horaFormatada = formatter.format(parser.parse(sunsetTime))
                txtSunset.text = "Por do sol é às $horaFormatada"
            } catch (ex: Exception) {

            }
        }

        override fun onPostExecute(result: String?) {

        }

    }

    fun ConvertStreamToString(input_Stream: InputStream): String {
        val bufferReader = BufferedReader(InputStreamReader(input_Stream))
        var line: String
        var allString = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    allString += line
                }
            } while (line != null)
            input_Stream.close()
        } catch (ex: Exception) {

        }

        return allString
    }
}
