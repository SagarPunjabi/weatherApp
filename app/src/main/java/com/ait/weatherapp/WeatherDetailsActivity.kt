package com.ait.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.ait.weatherapp.data.WeatherResult
import com.ait.weatherapp.network.WeatherAPI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_weather_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
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
                    MaterialDialog(this@WeatherDetailsActivity).show {
                        title(R.string.error)
                        message(R.string.city_not_found)
                        cancelable(false)
                        cancelOnTouchOutside(false)
                        positiveButton(R.string.go_back) { dialog ->
                            finish()
                        }
                    }
                } else {
                    var weatherData = response.body()

                    tvTemp.text = weatherData?.main?.temp.toString()
                    tvSunsetTime.text = weatherData?.sys?.sunset.toString()
                    tvSunRiseTime.text = {
                        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        val date = java.util.Date(weatherData?.sys?.sunrise.toString())
                        sdf.format(date)
                    }.toString()
                }

                Log.d("Success", "${response.message()}")
                Log.d("Success", "${response.body()}")
            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                MaterialDialog(this@WeatherDetailsActivity).show {
                    title(R.string.error)
                    message(text = t.message)
                    cancelable(false)
                    cancelOnTouchOutside(false)
                    positiveButton(R.string.go_back) { dialog ->
                        finish()
                    }
                }

            }
        })

    }
}
