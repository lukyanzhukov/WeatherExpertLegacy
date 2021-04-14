package com.lukianbat.weatherexpertlegacy.start.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lukianbat.weatherexpertlegacy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment(R.layout.fragment_start) {

    private val viewModel by viewModels<StartViewModel>()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onboardingPassed().observeData(viewLifecycleOwner, this::handleOnboardingPassed)
    }

    private fun handleOnboardingPassed(onboardingPassed: Boolean) {
        if (onboardingPassed) return navController.navigate(R.id.chooseCityAction)
        navController.navigate(R.id.onboardingAction)
    }
}