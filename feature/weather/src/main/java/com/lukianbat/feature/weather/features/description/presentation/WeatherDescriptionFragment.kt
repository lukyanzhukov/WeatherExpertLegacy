package com.lukianbat.feature.weather.features.description.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.lukianbat.architecture.mvvm.State
import com.lukianbat.coreui.utils.viewBinding
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.common.di.WeatherFlowComponentController
import com.lukianbat.feature.weather.databinding.FragmentWeatherDescriptionBinding
import javax.inject.Inject

class WeatherDescriptionFragment : Fragment(R.layout.fragment_weather_description) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by navGraphViewModels<WeatherDescriptionViewModel>(R.id.navigation_weather) { viewModelFactory }

    private val weatherNavController by lazy { requireActivity().findNavController(R.id.host_weather) }

    private val binding by viewBinding(FragmentWeatherDescriptionBinding::bind)

    private val saveButton get() = binding.saveButton
    private val descriptionFieldView get() = binding.descriptionFieldView
    private val weatherImageView get() = binding.weatherImageView
    private val windSpeedTextView get() = binding.windSpeedTextView
    private val humidityTextView get() = binding.humidityTextView
    private val dateTextView get() = binding.dateTextView
    private val tempTextView get() = binding.tempTextView

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
        (requireActivity() as OnBackPressedDispatcherOwner).onBackPressedDispatcher.addCallback(this) {
            requireActivity().findNavController(R.id.host_weather).popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.description().observe(viewLifecycleOwner, ::handleDescription)
        viewModel.onDescriptionSaved().observe(viewLifecycleOwner, ::handleDescriptionSaved)

        saveButton.setOnClickListener { viewModel.onSaveButtonClicked() }
        descriptionFieldView.addAfterTextChangedListener {
            viewModel.onDescriptionTextChanged(it)
        }
    }

    private fun handleDescriptionSaved(state: State<Unit>) {
        when (state) {
            is State.Completed -> {
                weatherNavController.navigate(
                    R.id.descriptionSavedAction,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.navigation_weather, true).build()
                )
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