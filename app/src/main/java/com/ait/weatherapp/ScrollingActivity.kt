package com.ait.weatherapp

import android.os.Bundle
import android.text.InputType
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.ait.weatherapp.data.WeatherResult
import com.ait.weatherapp.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_scrolling.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScrollingActivity : AppCompatActivity() {

    val type = InputType.TYPE_CLASS_TEXT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var weatherAPI = retrofit.create(WeatherAPI::class.java)
        var inputField: MaterialDialog

        fabAddCity.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            inputField = MaterialDialog(this).show {
                title(R.string.dialog_input)
                input(inputType = type)

                positiveButton(R.string.enter) { dialogBox ->
                    var weatherCall = weatherAPI.getWeatherDetails(
                        dialogBox.getInputField().text.toString(),
                        "imperial",
                        "9b4905abe2faa7c4b0235e30ec4af256"
                    )
                    weatherCall.enqueue(object : Callback<WeatherResult> {
                        override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                            Snackbar.make(view, "${response.message()}", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()

                            Log.d("Error","${response.message()}")
                        }
                        override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                            Snackbar.make(view, "${t.message}", Snackbar.LENGTH_LONG)
                                  .setAction("Action", null).show()

                            Log.d("Error","${t.message}")
                        }
                    })
                }
                negativeButton(R.string.cancel)
            }

        }

    }


}
