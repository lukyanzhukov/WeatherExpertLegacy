package com.lukianbat.feature.city.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.core.common.model.CityModel
import com.lukianbat.coreui.utils.bindView
import com.lukianbat.coreui.utils.inflate
import com.lukianbat.feature.city.R

internal class CitiesAdapter(
    private val selectCity: (CityModel) -> Unit
) : ListAdapter<CityListItem, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = parent.inflate(viewType)
        return when (viewType) {
            R.layout.item_city -> CityViewHolder(view)
            R.layout.item_city_selected -> CitySelectedViewHolder(view)
            R.layout.item_city_error -> ErrorViewHolder(view)
            else -> throw IllegalStateException("unsupported view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is CityListItem.CityItem -> R.layout.item_city
            is CityListItem.CitySelectedItem -> R.layout.item_city_selected
            is CityListItem.ErrorItem -> R.layout.item_city_error
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CityViewHolder -> {
                val item = getItem(position) as CityListItem.CityItem
                holder.bind(item, selectCity)
            }
            is ErrorViewHolder -> {
                val item = getItem(position) as CityListItem.ErrorItem
                holder.bind(item)
            }
        }
    }

    internal class CityViewHolder(
        private val containerView: View
    ) : RecyclerView.ViewHolder(containerView) {

        private val cityView: TextView by bindView(R.id.cityView)

        fun bind(item: CityListItem.CityItem, citySelectedListener: (CityModel) -> Unit) {
            cityView.text = "${item.city.name}, ${item.city.country}"
            containerView.setOnClickListener { citySelectedListener(item.city) }
        }
    }

    internal class CitySelectedViewHolder(containerView: View) :
        RecyclerView.ViewHolder(containerView)

    internal class ErrorViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

        private val errorView: TextView by bindView(R.id.errorTextView)

        fun bind(item: CityListItem.ErrorItem) {
            errorView.setText(item.textRes)
        }
    }

    internal object DiffCallback : DiffUtil.ItemCallback<CityListItem>() {
        override fun areItemsTheSame(oldItem: CityListItem, newItem: CityListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CityListItem, newItem: CityListItem): Boolean {
            return oldItem == newItem
        }
    }
}
