package com.lukianbat.feature.weather.features.weather.presentation.list.view

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.coreui.utils.bindView
import com.lukianbat.coreui.utils.getString
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem

class EditButtonItemHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {

    private val editButton: Button by bindView(R.id.editButton)

    fun bind(item: WeatherListItem.ButtonItem, onEditDescriptionClickListener: () -> Unit) {
        editButton.setOnClickListener { onEditDescriptionClickListener() }
        editButton.text = getString(item.textResId)
    }
}
