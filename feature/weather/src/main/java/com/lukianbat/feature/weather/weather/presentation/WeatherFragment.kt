package com.lukianbat.feature.weather.weather.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lukianbat.architecture.mvvm.State
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.di.WeatherFlowComponentController
import com.lukianbat.feature.weather.domain.model.WeatherModel
import javax.inject.Inject

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<WeatherViewModel> { viewModelFactory }

    private val weatherNavController by lazy { requireActivity().findNavController(R.id.host_weather) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as WeatherFlowComponentController)
            .provideWeatherFlowComponent()
            .weatherComponent()
            .create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.weather().observe(viewLifecycleOwner, ::handleWeather)
    }

    private fun handleWeather(state: State<WeatherModel>) {
        when (state) {
            State.Loading -> Log.i("TESTLOG", "State.Loading")
            is State.Error -> Log.i("TESTLOG", "State.Error "+state.error)
            is State.Completed -> Log.i("TESTLOG", "State.Completed "+state.data)
        }
    }
}
