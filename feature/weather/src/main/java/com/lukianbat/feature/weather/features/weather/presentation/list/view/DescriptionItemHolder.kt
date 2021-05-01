package com.lukianbat.feature.weather.features.weather.presentation.list.view

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.lukianbat.coreui.utils.bindView
import com.lukianbat.coreui.utils.getString
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem

class DescriptionItemHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {

    private val editText: EditText by bindView(R.id.descriptionTextView)
    private val textInputLayout: TextInputLayout by bindView(R.id.descriptionTextInputLayout)

    fun bind(item: WeatherListItem.DescriptionItem) {
        textInputLayout.isEnabled = false
        val note = if (item.note.isNotBlank()) item.note else getString(R.string.weather_description_empty)
        editText.setText(note)
    }
}
