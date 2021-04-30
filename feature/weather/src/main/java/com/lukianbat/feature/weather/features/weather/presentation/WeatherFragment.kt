package com.lukianbat.feature.weather.features.weather.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lukianbat.architecture.mvvm.State
import com.lukianbat.coreui.dialogs.AlertBottomSheetDialog
import com.lukianbat.coreui.dialogs.RadioBottomSheetDialog
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.common.di.WeatherFlowComponentController
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListAdapter
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem
import kotlinx.android.synthetic.main.fragment_weather.*
import javax.inject.Inject

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val weatherNavController by lazy { requireActivity().findNavController(R.id.host_weather) }
    private val globalNavController by lazy { requireActivity().findNavController(R.id.host_global) }

    private val viewModel by viewModels<WeatherViewModel> { viewModelFactory }

    private val weatherListAdapter by lazy {
        WeatherListAdapter(viewModel::onRefreshButtonClicked) {
            weatherNavController.navigate(R.id.editDescriptionAction)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as WeatherFlowComponentController)
            .provideWeatherFlowComponent()
            .weatherComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.weather().observe(this, ::handleWeatherItems)
        viewModel.chosenCity().observe(this, ::handleChosenCity)
        viewModel.savedCities().observe(this, ::handleSavedCities)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showLoadingError().observeData(viewLifecycleOwner, ::showLoadingError)

        searchCityButton.setOnClickListener { globalNavController.navigate(R.id.searchCityAction) }
        cityTextView.setOnClickListener { viewModel.onCityClicked() }
        recyclerView.adapter = weatherListAdapter
    }

    private fun handleWeatherItems(state: State<List<WeatherListItem>>) {
        when (state) {
            is State.Loading -> {
                setProgress(true)
                recyclerView.isVisible = false
            }
            is State.Error -> {
                setProgress(false)
                showLoadingError(true)
                val dialog = AlertBottomSheetDialog.Builder(requireActivity())
                    .setTitle(R.string.weather_loading_list_error_title)
                    .setMessage(R.string.weather_loading_list_error_message)
                    .setPositiveClickListener { state.retry() }
                    .setNegativeClickListener { it.dismiss() }
                    .build()

                dialog.show()
            }
            is State.Completed -> {
                setProgress(false)
                recyclerView.isVisible = true
                weatherListAdapter.submitList(state.data)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleChosenCity(state: State<CityUIModel>) {
        when (state) {
            is State.Completed -> {
                cityTextView.text = "${state.data.cityName}, ${state.data.countryName}"
            }
        }
    }

    private fun handleSavedCities(state: State<List<CityUIModel>>) {
        when (state) {
            is State.Completed -> {
                val cities = state.data
                RadioBottomSheetDialog(
                    context = requireContext(),
                    items = cities,
                    title = getString(R.string.weather_saved_cities_dialog_title),
                    convertItemFunc = { "${it.cityName}, ${it.countryName}" },
                    onItemClickListener = { itemIndex ->
                        viewModel.onCitySelected(cities[itemIndex])
                    }
                )
                    .show()
            }
        }
    }

    private fun showLoadingError(show: Boolean) {
        recyclerView.isVisible = true
        if (show) {
            weatherListAdapter.setHasError(true) { recyclerView.scrollToPosition(0) }
            return
        }
        weatherListAdapter.setHasError(false)
    }

    private fun setProgress(progress: Boolean) {
        if (progress) {
            shimmerLayout.isVisible = true
            shimmerLayout.startShimmer()
            return
        }
        shimmerLayout.isVisible = false
        shimmerLayout.stopShimmer()
    }
}
