package com.lukianbat.feature.weather.features.weather.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.coreui.utils.inflate
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.features.weather.presentation.list.view.*
import kotlinx.android.synthetic.main.item_weather_edit_button.view.*

class WeatherListAdapter(
    private val refreshClickListener: () -> Unit,
    private val onEditDescriptionClickListener: () -> Unit
) : ListAdapter<WeatherListItem, RecyclerView.ViewHolder>(WeatherListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = parent.inflate(viewType)
        return when (viewType) {
            R.layout.item_weather_title -> TitleItemHolder(view)
            R.layout.item_weather_card -> WeatherCardItemHolder(view)
            R.layout.item_weather_description_field -> DescriptionItemHolder(view)
            R.layout.item_weather_edit_button -> {
                view.editButton.setOnClickListener { onEditDescriptionClickListener() }
                EditButtonItemHolder(view)
            }
            R.layout.item_weather_divider -> DividerViewHolder(view)
            R.layout.item_weather_error -> ErrorItemHolder(view)
            else -> throw IllegalStateException("unsupported view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is WeatherListItem.TitleItem -> R.layout.item_weather_title
            is WeatherListItem.WeatherCardItem -> R.layout.item_weather_card
            is WeatherListItem.DescriptionItem -> R.layout.item_weather_description_field
            is WeatherListItem.ButtonItem -> R.layout.item_weather_edit_button
            is WeatherListItem.Divider -> R.layout.item_weather_divider
            is WeatherListItem.ErrorItem -> R.layout.item_weather_error
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleItemHolder -> {
                val item = currentList[position] as WeatherListItem.TitleItem
                holder.bind(item)
            }

            is WeatherCardItemHolder -> {
                val item = currentList[position] as WeatherListItem.WeatherCardItem
                holder.bind(item)
            }

            is DescriptionItemHolder -> {
                val item = currentList[position] as WeatherListItem.DescriptionItem
                holder.bind(item)
            }

            is EditButtonItemHolder -> {
                val item = currentList[position] as WeatherListItem.ButtonItem
                holder.bind(item)
            }

            is ErrorItemHolder -> {
                holder.bind(refreshClickListener)
            }
        }
    }

    fun setHasError(value: Boolean, onListUpdated: (() -> Unit)? = null) {
        onListUpdated?.let { callback ->
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    unregisterAdapterDataObserver(this)
                    callback()
                }
            })
        }
        submitList(
            currentList.toMutableList().apply {
                when {
                    value && firstOrNull() != WeatherListItem.ErrorItem -> {
                        add(
                            0,
                            WeatherListItem.ErrorItem
                        )
                    }
                    !value && firstOrNull() == WeatherListItem.ErrorItem -> removeAt(0)
                }
            })
    }
}
