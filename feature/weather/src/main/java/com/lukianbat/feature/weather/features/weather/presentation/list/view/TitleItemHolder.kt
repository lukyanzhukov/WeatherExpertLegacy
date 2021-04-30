package com.lukianbat.feature.weather.features.weather.presentation.list.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.coreui.utils.bindView
import com.lukianbat.coreui.utils.getString
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem

class TitleItemHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {

    private val subtitleTextView: TextView by bindView(R.id.subtitleTextView)

    fun bind(item: WeatherListItem.TitleItem) {
        subtitleTextView.text = getString(item.textResId)
    }
}
