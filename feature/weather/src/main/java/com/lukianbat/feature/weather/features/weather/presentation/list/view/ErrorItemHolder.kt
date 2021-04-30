package com.lukianbat.feature.weather.features.weather.presentation.list.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.coreui.utils.bindView
import com.lukianbat.coreui.utils.getString
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem

class ErrorItemHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {

    private val refreshButton: Button by bindView(R.id.refreshButton)

    fun bind(onRefreshClick: () -> Unit) {
        refreshButton.setOnClickListener { onRefreshClick() }
    }
}
