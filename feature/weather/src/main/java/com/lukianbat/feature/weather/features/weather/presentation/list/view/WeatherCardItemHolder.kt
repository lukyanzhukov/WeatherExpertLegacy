package com.lukianbat.feature.weather.features.weather.presentation.list.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.coreui.utils.bindView
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem

class WeatherCardItemHolder(
    private val containerView: View
) : RecyclerView.ViewHolder(containerView) {

    private val weatherImageView: ImageView by bindView(R.id.weatherImageView)
    private val tempTextView: TextView by bindView(R.id.tempTextView)
    private val feelingsTempTextView: TextView by bindView(R.id.feelingsTempTextView)
    private val windSpeedTextView: TextView by bindView(R.id.windSpeedTextView)
    private val humidityTextView: TextView by bindView(R.id.humidityTextView)
    private val minTempTextView: TextView by bindView(R.id.minTempTextView)
    private val maxTempTextView: TextView by bindView(R.id.maxTempTextView)
    private val dateTextView: TextView by bindView(R.id.dateTextView)

    fun bind(item: WeatherListItem.WeatherCardItem) {
        weatherImageView.setImageResource(item.weatherImageResId)
        containerView.context.apply {
            tempTextView.text = getString(R.string.weather_temp_label, item.temp)
            feelingsTempTextView.text = getString(
                R.string.weather_feelings_label, item.feelsLikeTemp
            )
            windSpeedTextView.text = getString(R.string.weather_wind_speed_label, item.windSpeed)
            humidityTextView.text = getString(R.string.weather_humidity_label, item.humidity)
            minTempTextView.text = getString(R.string.weather_min_temp_label, item.minTemp)
            maxTempTextView.text = getString(R.string.weather_max_temp_label, item.maxTemp)
            dateTextView.text = item.date
        }
    }
}
