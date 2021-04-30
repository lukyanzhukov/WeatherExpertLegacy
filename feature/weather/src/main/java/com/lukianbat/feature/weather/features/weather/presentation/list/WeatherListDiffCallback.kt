package com.lukianbat.feature.weather.features.weather.presentation.list

import androidx.recyclerview.widget.DiffUtil

object WeatherListDiffCallback : DiffUtil.ItemCallback<WeatherListItem>() {
    override fun areItemsTheSame(oldItem: WeatherListItem, newItem: WeatherListItem): Boolean {
        return if (oldItem is WeatherListItem.WeatherCardItem && newItem is WeatherListItem.WeatherCardItem)
            oldItem.date == newItem.date
        else if (oldItem is WeatherListItem.DescriptionItem && newItem is WeatherListItem.DescriptionItem)
            oldItem.note == newItem.note
        else if (oldItem is WeatherListItem.ButtonItem && newItem is WeatherListItem.ButtonItem)
            oldItem.textResId == newItem.textResId
        else if (oldItem is WeatherListItem.TitleItem && newItem is WeatherListItem.TitleItem)
            oldItem.textResId == newItem.textResId
        else if (oldItem is WeatherListItem.ErrorItem && newItem is WeatherListItem.ErrorItem) true
        else oldItem is WeatherListItem.Divider && newItem is WeatherListItem.Divider
    }

    override fun areContentsTheSame(oldItem: WeatherListItem, newItem: WeatherListItem): Boolean {
        return if (oldItem is WeatherListItem.WeatherCardItem && newItem is WeatherListItem.WeatherCardItem)
            oldItem == newItem
        else if (oldItem is WeatherListItem.DescriptionItem && newItem is WeatherListItem.DescriptionItem)
            oldItem == newItem
        else if (oldItem is WeatherListItem.ButtonItem && newItem is WeatherListItem.ButtonItem)
            oldItem == newItem
        else if (oldItem is WeatherListItem.TitleItem && newItem is WeatherListItem.TitleItem)
            oldItem == newItem
        else if (oldItem is WeatherListItem.ErrorItem && newItem is WeatherListItem.ErrorItem) true
        else oldItem is WeatherListItem.Divider && newItem is WeatherListItem.Divider
    }
}
