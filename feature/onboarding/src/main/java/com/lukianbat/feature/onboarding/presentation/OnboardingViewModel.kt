package com.lukianbat.feature.onboarding.presentation

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.core.gateway.SettingsGateway
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val settingsGateway: SettingsGateway
) : RxViewModel() {

    private val onCompleteAction = RxViewOutput<Unit>(this, RxViewOutput.Strategy.ONCE)

    fun completeAction() = onCompleteAction

    fun complete() {
        settingsGateway.saveSettings(
            settingsGateway.settings.copy(isOnboardingPassed = true)
        )
        onCompleteAction.valueSource(Unit)
    }
}
