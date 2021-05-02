package com.lukianbat.feature.weather.features.weather.presentation.list.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.coreui.utils.bindView
import com.lukianbat.coreui.utils.getString
import com.lukianbat.coreui.view.WeatherExpertFieldView
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem

class DescriptionItemHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {

    private val fieldView: WeatherExpertFieldView by bindView(R.id.descriptionFieldView)

    fun bind(item: WeatherListItem.DescriptionItem) {
        fieldView.setLocked(true)
        val note = if (item.note.isNotBlank()) item.note else getString(R.string.weather_description_empty)
        fieldView.setText(note)
    }
}
