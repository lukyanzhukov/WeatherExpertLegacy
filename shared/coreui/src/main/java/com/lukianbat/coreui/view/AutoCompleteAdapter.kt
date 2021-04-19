package com.lukianbat.coreui.view

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import java.util.*

abstract class AutoCompleteAdapter<T> : BaseAdapter(), Filterable {
    private val itemsList: MutableList<T> = ArrayList()
    private val filteredItems: MutableList<T> = ArrayList()
    private var filter: CharSequence? = null

    abstract override fun getView(i: Int, convertView: View?, group: ViewGroup): View

    protected abstract fun convertResultToString(resultValue: T): CharSequence

    protected abstract fun filterItem(sequence: CharSequence?, item: T): Boolean

    override fun getCount(): Int = filteredItems.size

    override fun getItem(i: Int): T = filteredItems[i]

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getFilter(): Filter = AutoCompleteFilter()

    fun setItems(items: List<T>) {
        itemsList.clear()
        filteredItems.clear()
        itemsList.addAll(items)
        filteredItems.addAll(filterItems(filter))
        notifyDataSetChanged()
    }

    fun clear() {
        itemsList.clear()
        filteredItems.clear()
        notifyDataSetChanged()
    }

    private fun filterItems(sequence: CharSequence?): List<T> {
        val filteredItems: MutableList<T> = ArrayList()
        if (sequence == null) {
            filteredItems.addAll(itemsList)
        } else {
            for (item in itemsList) {
                if (filterItem(sequence, item)) {
                    filteredItems.add(item)
                }
            }
        }
        return filteredItems
    }

    private inner class AutoCompleteFilter : Filter() {
        override fun performFiltering(sequence: CharSequence?): FilterResults {
            filter = sequence
            val filterResults = FilterResults()
            val filteredCities = filterItems(sequence)
            filterResults.values = filteredCities
            filterResults.count = filteredCities.size
            return filterResults
        }

        override fun publishResults(sequence: CharSequence?, results: FilterResults) {
            if (results.count > 0) {
                filteredItems.clear()
                filteredItems.addAll((results.values as List<T>))
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

        override fun convertResultToString(resultValue: Any): CharSequence {
            return this@AutoCompleteAdapter.convertResultToString(resultValue as T)
        }
    }
}