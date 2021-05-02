package com.lukianbat.feature.weather

import android.content.Context
import android.os.Bundle
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lukianbat.coreui.utils.argument
import com.lukianbat.feature.weather.common.di.WeatherFlowComponentController
import javax.inject.Inject

class WeatherFlowFragment : Fragment(R.layout.fragment_weather_flow) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<WeatherFlowViewModel> { viewModelFactory }

    private val argCityName by argument { getString(CHOSEN_CITY_NAME, "") }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as WeatherFlowComponentController)
            .provideWeatherFlowComponent()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCityName(argCityName)
        (requireActivity() as OnBackPressedDispatcherOwner).onBackPressedDispatcher.addCallback(this) {
            requireActivity().findNavController(R.id.host_global).popBackStack()
        }
    }

    companion object {
        private const val CHOSEN_CITY_NAME = "chosen_city_name"
    }
}
