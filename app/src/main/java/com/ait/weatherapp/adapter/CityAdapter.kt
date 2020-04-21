package com.ait.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ait.weatherapp.R
import com.ait.weatherapp.data.City
import kotlinx.android.synthetic.main.city_row.view.*

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder> {

    var citiesList = mutableListOf<City>()

    val context: Context

    constructor(context: Context) {
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.city_row, parent, false
        )
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return citiesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = citiesList[position]

        holder.tvCityName.text = currentCity.cityName

        holder.btnDelete.setOnClickListener{
            deleteCity(holder.adapterPosition)
        }
        
    }

    private fun deleteCity(position: Int)
    {
        citiesList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun addCity(city: City){
        citiesList.add(city)
        notifyItemInserted(citiesList.lastIndex)
    }

    inner class ViewHolder(cityView: View) : RecyclerView.ViewHolder(cityView){
        val tvCityName = cityView.tvCityName
        val btnDelete = cityView.imgbtnDelete
        val btnDetails = cityView.btnClickForDetails
    }
}