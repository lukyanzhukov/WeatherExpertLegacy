package com.lukianbat.feature.city.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.gojuno.koptional.Optional
import com.lukianbat.architecture.mvvm.State
import com.lukianbat.core.common.model.CityModel
import com.lukianbat.coreui.utils.addAfterTextChangedListener
import com.lukianbat.coreui.utils.viewBinding
import com.lukianbat.feature.city.R
import com.lukianbat.feature.city.databinding.FragmentChooseCityBinding
import com.lukianbat.feature.city.di.CityComponentController
import javax.inject.Inject

class ChooseCityFragment : Fragment(R.layout.fragment_choose_city) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<ChooseCityViewModel>(R.id.navigation_global) { viewModelFactory }

    private val navController by lazy { findNavController() }

    private val binding by viewBinding(FragmentChooseCityBinding::bind)

    private val nextButton get() = binding.nextButton
    private val recyclerView get() = binding.recyclerView
    private val searchCityView get() = binding.searchCityView

    private lateinit var searchCityAdapter: CitiesAdapter

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
        viewModel.savedCity().observeData(viewLifecycleOwner, ::handleSavedCity)
    }

    private fun initSearchCityView() {
        searchCityAdapter = CitiesAdapter {
            viewModel.onCitySelected(it)
        }
        recyclerView.adapter = searchCityAdapter
        searchCityView.addAfterTextChangedListener { viewModel.onCityNameChanged(it) }
    }

    private fun handleOnNext(state: State<CityModel>) {
        when (state) {
            is State.Completed -> {
                val bundle = bundleOf(CHOSEN_CITY_NAME to state.data.name)
                navController.navigate(R.id.cityChosenAction, bundle)
            }
        }
    }

    private fun handleSavedCity(city: Optional<CityModel>) {
        city.toNullable()?.let {
            nextButton.isEnabled = true
            searchCityView.setText(it.name)
            hideKeyboard()
            searchCityAdapter.submitList(listOf(CityListItem.CitySelectedItem))
            return
        }
        nextButton.isEnabled = false
        searchCityAdapter.submitList(listOf())
    }

    private fun handleCitiesList(state: State<CitiesSearchAction>) {
        when (state) {
            is State.Error -> {
                searchCityAdapter.submitList(
                    listOf(CityListItem.ErrorItem(R.string.choose_city_error_text))
                )
            }
            is State.Completed -> {
                when (val action = state.data) {
                    is CitiesSearchAction.CitiesFound -> {
                        searchCityAdapter.submitList(action.cities)
                    }
                    CitiesSearchAction.CitiesNotFound -> {
                        searchCityAdapter.submitList(
                            listOf(CityListItem.ErrorItem(R.string.choose_city_not_found_error_text))
                        )
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchCityView.windowToken, 0)
    }

    companion object {
        private const val CHOSEN_CITY_NAME = "chosen_city_name"
    }
}