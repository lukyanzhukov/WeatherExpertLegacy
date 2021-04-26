package com.lukianbat.weatherexpertlegacy.start.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lukianbat.weatherexpertlegacy.R
import com.lukianbat.weatherexpertlegacy.start.di.StartFragmentComponentController
import javax.inject.Inject

class StartFragment : Fragment(R.layout.fragment_start) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<StartViewModel> { viewModelFactory }

    private val navController by lazy { findNavController() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectThis()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onboardingPassed().observeData(viewLifecycleOwner, this::handleOnboardingPassed)
    }

    private fun injectThis() {
        (requireActivity().applicationContext as StartFragmentComponentController)
            .provideStartFragmentComponent()
            .inject(this)
    }

    private fun handleOnboardingPassed(onboardingPassed: Boolean) {
        if (onboardingPassed) return navController.navigate(R.id.chooseCityAction)
        navController.navigate(R.id.onboardingAction)
    }
}