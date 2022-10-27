package com.eloyan.weathereloyan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eloyan.weathereloyan.R
import com.eloyan.weathereloyan.databinding.CardviewWeatherListItemBinding
import com.eloyan.weathereloyan.model.weather.data.WeatherData
import com.squareup.picasso.Picasso

class WeatherListAdapter :
    ListAdapter<WeatherData, WeatherListAdapter.WeatherItemHolder>(CallbackImpl()) {
    class WeatherItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CardviewWeatherListItemBinding.bind(view)

        fun bind(item: WeatherData) = with(binding) {
            val temp =
                item.currentTemperature.ifEmpty { "${item.maxTemperature}/${item.minTemperature}" }
            tvItemDate.text = item.date
            tvItemCondition.text = item.conditionText
            tvTemperature.text = "${temp}°C"
            Picasso.get().load("https:${item.conditionImageUrl}").into(imgDailyCondition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_weather_list_item, parent, false)
        return WeatherItemHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CallbackImpl : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData) =
            oldItem == newItem
    }
}