package com.ait.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.ait.weatherapp.data.WeatherResult
import com.ait.weatherapp.network.WeatherAPI
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

//        var retrofit = Retrofit.Builder()
//            .baseUrl("https://api.openweathermap.org")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        var weatherAPI = retrofit.create(WeatherAPI::class.java)
//        var inputField: MaterialDialog


//        var weatherCall = weatherAPI.getWeatherDetails(
//            dialogBox.getInputField().text.toString(),
//            "imperial",
//            "9b4905abe2faa7c4b0235e30ec4af256"
//        )
//        weatherCall.enqueue(object : Callback<WeatherResult> {
//            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
//                Snackbar.make(view, "${response.message()}", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//
//                Log.d("Success","${response.message()}")
//                Log.d("Success","${response.body()}")
//            }
//            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
//                Snackbar.make(view, "${t.message}", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//
//                Log.d("Error","${t.message}")
//            }
//        })

    }
}
