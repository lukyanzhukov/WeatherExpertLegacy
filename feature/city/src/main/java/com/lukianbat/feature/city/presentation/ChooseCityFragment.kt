package com.lukianbat.feature.city.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.gojuno.koptional.Optional
import com.lukianbat.architecture.mvvm.State
import com.lukianbat.core.common.model.CityModel
import com.lukianbat.coreui.utils.addAfterTextChangedListener
import com.lukianbat.feature.city.R
import com.lukianbat.feature.city.di.CityComponentController
import kotlinx.android.synthetic.main.fragment_choose_city.*
import javax.inject.Inject

class ChooseCityFragment : Fragment(R.layout.fragment_choose_city) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<ChooseCityViewModel>(R.id.navigation_global) { viewModelFactory }

    private val navController by lazy { findNavController() }

    private val searchCityAdapter: CitiesAdapter by lazy { CitiesAdapter(requireContext()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as CityComponentController)
            .provideCityComponent()
            .inject(this)
    }

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
                val bundle = bundleOf(CHOSEN_CITY_NAME to state.data.name)
                navController.navigate(R.id.cityChosenAction, bundle)
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

    companion object {
        private const val CHOSEN_CITY_NAME = "chosen_city_name"
    }
}