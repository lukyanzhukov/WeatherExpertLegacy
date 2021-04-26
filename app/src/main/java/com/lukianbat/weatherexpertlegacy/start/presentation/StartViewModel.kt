package com.lukianbat.weatherexpertlegacy.start.presentation

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.core.common.gateway.SettingsGateway
import com.lukianbat.weatherexpertlegacy.start.di.StartFragmentComponentController
import javax.inject.Inject

class StartViewModel @Inject constructor(
    settingsGateway: SettingsGateway,
    private val startFragmentComponentController: StartFragmentComponentController
) : RxViewModel() {

    private val onboardingPassed = RxViewOutput<Boolean>(this)

    init {
        onboardingPassed.valueSource(settingsGateway.settings.isOnboardingPassed)
    }

    fun onboardingPassed() = onboardingPassed

    override fun onCleared() {
        super.onCleared()
        startFragmentComponentController.clearStartFragmentComponent()
    }
}
