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
import com.ait.weatherapp.adapter.CityAdapter
import com.ait.weatherapp.data.City
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
    lateinit var cityAdapter: CityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        cityAdapter = CityAdapter(this)
        recyclerCityList.adapter = cityAdapter

        fabAddCity.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            MaterialDialog(this).show {
                title(R.string.dialog_input)
                input(inputType = type)

                positiveButton(R.string.enter) { dialogBox ->
                    cityAdapter.addCity(City(dialogBox.getInputField().text.toString()))
                }
                negativeButton(R.string.cancel)
            }

        }

    }


}
