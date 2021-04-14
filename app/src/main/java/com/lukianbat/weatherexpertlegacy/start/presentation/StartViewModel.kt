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

    private val isFirstLaunch = RxViewOutput<Boolean>(this)

    init {
        isFirstLaunch.valueSource(settingsGateway.settings.isOnboardingPassed)
    }

    fun onboardingPassed() = isFirstLaunch
}
