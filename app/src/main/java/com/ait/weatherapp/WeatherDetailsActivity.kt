package com.ait.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.ait.weatherapp.data.WeatherResult
import com.ait.weatherapp.network.WeatherAPI
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_weather_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.weathermap) as SupportMapFragment
        mapFragment.getMapAsync(this)


        var city = intent.getStringExtra("CITY_NAME")
        tvCity.text = city

        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var weatherAPI = retrofit.create(WeatherAPI::class.java)


        var weatherCall = weatherAPI.getWeatherDetails(
            city,
            "imperial",
            "9b4905abe2faa7c4b0235e30ec4af256"
        )




        weatherCall.enqueue(object : Callback<WeatherResult> {
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                if (response.message().equals("Not Found")) {
                    showErrorDialog()
                } else {
                    var weatherData = response.body()

                    tvTemp.text = weatherData?.main?.temp.toString()

                    tvWeatherDescription.text = weatherData?.weather?.get(0)?.description.toString()

                    val sunSet = response.body()?.sys?.sunset!!.toLong()
                    tvSunSetTime.text = setSunTime(sunSet)

                    val sunRise = response.body()?.sys?.sunrise!!.toLong()
                    tvSunRiseTime.text = setSunTime(sunRise)

                    tvHumidity.text = weatherData?.main?.humidity.toString()

                    Glide.with(this@WeatherDetailsActivity)
                        .load(
                            ("https://openweathermap.org/img/w/" +
                                    response.body()?.weather?.get(0)?.icon
                                    + ".png")
                        )
                        .into(ivWeatherIcon)
                    var cityLat = weatherData?.coord?.lat!!.toDouble()
                    var cityLon = weatherData?.coord?.lon!!.toDouble()
                    val city = LatLng(cityLat,cityLon)
                    mMap.addMarker(MarkerOptions().position(city).title("Marker in $city"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city,5f))
                    
                }

                Log.d("Success", "${response.message()}")
                Log.d("Success", "${response.body()}")
            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                showErrorDialog(true, t.message)
            }
        })

    }

    fun showErrorDialog(onFailure: Boolean = false, errorMessage: String? = "") {
        MaterialDialog(this@WeatherDetailsActivity).show {
            title(R.string.error)
            if (!onFailure) message(R.string.city_not_found) else message(text = errorMessage)
            cancelable(false)
            cancelOnTouchOutside(false)
            positiveButton(R.string.go_back) { dialog ->
                finish()
            }
        }
    }

    fun setSunTime(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(time * 1000)
        return SimpleDateFormat("HH:mm").format(calendar.time)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
