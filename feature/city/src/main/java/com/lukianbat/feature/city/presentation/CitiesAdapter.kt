package com.lukianbat.feature.city.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lukianbat.coreui.view.AutoCompleteAdapter
import com.lukianbat.core.common.model.CityModel

class CitiesAdapter(private val context: Context) : AutoCompleteAdapter<CityModel>() {

    override fun getView(i: Int, convertView: View?, group: ViewGroup): View {
        val viewHolder: CitiesViewHolder
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, group, false)
            viewHolder = CitiesViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as CitiesViewHolder
        }
        viewHolder.bind(getItem(i))
        return view
    }

    override fun filterItem(sequence: CharSequence?, item: CityModel): Boolean = true

    override fun convertResultToString(resultValue: CityModel): CharSequence = resultValue.name

    class CitiesViewHolder(view: View) {
        private val nameTextView: TextView = view.findViewById(android.R.id.text1)
        private val infoTextView: TextView = view.findViewById(android.R.id.text2)

        fun bind(item: CityModel) {
            nameTextView.text = item.name
            infoTextView.text = item.countryCode
        }
    }
}
