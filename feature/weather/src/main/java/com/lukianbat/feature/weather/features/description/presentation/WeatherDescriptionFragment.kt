package com.lukianbat.feature.weather.features.description.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lukianbat.architecture.mvvm.State
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.common.di.WeatherFlowComponentController
import kotlinx.android.synthetic.main.fragment_weather_description.*
import javax.inject.Inject

class WeatherDescriptionFragment : Fragment(R.layout.fragment_weather_description) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<WeatherDescriptionViewModel> { viewModelFactory }

    private val weatherNavController by lazy { requireActivity().findNavController(R.id.host_weather) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as WeatherFlowComponentController)
            .provideWeatherFlowComponent()
            .weatherDescriptionComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.description().observe(this, ::handleDescription)
        viewModel.onDescriptionSaved().observe(this, ::handleDescriptionSaved)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener { viewModel.onSaveButtonClicked() }
        descriptionFieldView.addAfterTextChangedListener {
            viewModel.onDescriptionTextChanged(it)
        }
    }

    private fun handleDescriptionSaved(state: State<Unit>) {
        when (state) {
            is State.Completed -> {
                weatherNavController.navigate(R.id.descriptionSavedAction)
            }
        }
    }

    private fun handleDescription(state: State<DescriptionUIModel>) {
        when (state) {
            State.Loading -> {
            }
            is State.Error -> {
            }
            is State.Completed -> {
                weatherImageView.setImageResource(state.data.iconRes)
                tempTextView.text = getString(R.string.weather_temp_label, state.data.temp)
                windSpeedTextView.text =
                    getString(R.string.weather_wind_speed_label, state.data.windSpeed)
                humidityTextView.text =
                    getString(R.string.weather_humidity_label, state.data.humidity)
                dateTextView.text = state.data.date
                descriptionFieldView.setText(state.data.description)
            }
        }
    }
}