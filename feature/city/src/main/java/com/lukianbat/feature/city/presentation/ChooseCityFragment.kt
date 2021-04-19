package com.lukianbat.feature.city.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gojuno.koptional.Optional
import com.lukianbat.architecture.mvvm.State
import com.lukianbat.coreui.utils.addAfterTextChangedListener
import com.lukianbat.feature.city.R
import com.lukianbat.feature.city.domain.model.CityModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_choose_city.*

@AndroidEntryPoint
class ChooseCityFragment : Fragment(R.layout.fragment_choose_city) {

    private val viewModel by viewModels<ChooseCityViewModel>()
    private val navController by lazy { findNavController() }

    private val searchCityAdapter: CitiesAdapter by lazy { CitiesAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchCityView()

        nextButton.setOnClickListener { viewModel.onNextButtonClicked() }

        viewModel.cities().observe(viewLifecycleOwner, ::handleCitiesList)
        viewModel.onNext().observe(viewLifecycleOwner, ::handleOnNext)
        viewModel.enableNextButton().observeData(viewLifecycleOwner, ::handleEnableNextButton)
        viewModel.savedCity().observe(viewLifecycleOwner, ::handleSavedCity)
    }

    private fun initSearchCityView() {
        searchCityView.setOnItemClickListener { _, _, position, _ ->
            viewModel.onCitySelected(searchCityAdapter.getItem(position))
        }
        searchCityView.setAdapter(searchCityAdapter)
        searchCityView.setOnItemClickListener { _, _, position, _ ->
            viewModel.onCitySelected(searchCityAdapter.getItem(position))
        }
        searchCityView.addAfterTextChangedListener { viewModel.onCityNameChanged(it) }
    }

    private fun handleEnableNextButton(show: Boolean) {
        nextButton.isEnabled = show
    }

    private fun handleOnNext(state: State<CityModel>) {
        when (state) {
            is State.Completed -> {
//                navController.navigate(R.id.cityChosenAction)
            }
        }
    }

    private fun handleSavedCity(state: State<Optional<CityModel>>) {
        when (state) {
            is State.Completed -> {
                state.data.toNullable()?.let {
                    nextButton.isEnabled = true
                    searchCityView.setText(it.name)
                }
            }
        }
    }

    private fun handleCitiesList(state: State<CitiesSearchAction>) {
        when (state) {
            is State.Completed -> {
                when (val action = state.data) {
                    is CitiesSearchAction.CitiesFound -> {
                        searchCityAdapter.setItems(action.cities)
                    }
                    CitiesSearchAction.CitiesNotFound -> {

                    }
                    CitiesSearchAction.WrongCitiesFormatInput -> {

                    }
                }
            }
        }
    }
}