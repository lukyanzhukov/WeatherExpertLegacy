package com.lukianbat.feature.onboarding.presentation.presentation

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.core.common.gateway.SettingsGateway
import com.lukianbat.feature.onboarding.presentation.di.OnboardingComponentController
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val settingsGateway: SettingsGateway,
    private val componentController: OnboardingComponentController
) : RxViewModel() {

    private val onCompleteAction = RxViewOutput<Unit>(this, RxViewOutput.Strategy.ONCE)

    fun completeAction() = onCompleteAction.asOutput()

    fun complete() {
        settingsGateway.saveSettings(
            settingsGateway.settings.copy(isOnboardingPassed = true)
        )
        onCompleteAction.valueSource(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        componentController.clearOnboardingComponent()
    }
}
