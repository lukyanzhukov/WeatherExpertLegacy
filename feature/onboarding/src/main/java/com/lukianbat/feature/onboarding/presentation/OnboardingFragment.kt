package com.lukianbat.feature.onboarding.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lukianbat.feature.onboarding.R

class OnboardingFragment() : Fragment(R.layout.fragment_onboarding) {

    private val viewModel by viewModels<OnboardingViewModel>()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController.navigate(R.id.onboardingPassedAction)
    }
}