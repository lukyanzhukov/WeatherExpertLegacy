package com.lukianbat.weatherexpertlegacy.start.presentation

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.core.gateway.SettingsGateway
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    settingsGateway: SettingsGateway
) : RxViewModel() {

    private val onboardingPassed = RxViewOutput<Boolean>(this)

    init {
        onboardingPassed.valueSource(settingsGateway.settings.isOnboardingPassed)
    }

    fun onboardingPassed() = onboardingPassed
}
