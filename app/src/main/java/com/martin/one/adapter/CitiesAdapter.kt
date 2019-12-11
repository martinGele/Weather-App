package com.martin.one.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.martin.one.R
import com.martin.one.databinding.ItemForecastCitiesBinding
import com.martin.weatherestonia.model.Places

class CitiesAdapter(private val forecastList: ArrayList<Places?>) :
    RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemForecastCitiesBinding>(
                inflater,
                R.layout.item_forecast_cities,
                parent,
                false
            )

        return CitiesViewHolder(view)


    }

    override fun getItemCount(): Int {

        return forecastList.size

    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.view.weather = forecastList[position]
    }


    fun updateCitiesList(forecasts: List<Places?>) {
        forecastList.clear()
        forecastList.addAll(forecasts)
        notifyDataSetChanged()
    }

    class CitiesViewHolder(var view: ItemForecastCitiesBinding) : RecyclerView.ViewHolder(view.root)

}