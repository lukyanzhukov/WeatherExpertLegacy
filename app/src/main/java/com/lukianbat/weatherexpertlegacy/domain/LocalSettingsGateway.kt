package com.lukianbat.weatherexpertlegacy.domain

import com.lukianbat.core.common.model.Settings
import com.lukianbat.core.common.gateway.SettingsGateway
import com.lukianbat.prefser.Prefser

class LocalSettingsGateway(private val prefser: Prefser) : SettingsGateway {

    override fun saveSettings(settings: Settings) {
        prefser.preferences.edit().apply {
            putBoolean(IS_ONBOARDING_PASSED, settings.isOnboardingPassed)
            apply()
        }
    }

    override val settings: Settings
        get() = with(prefser.preferences) {
            Settings(isOnboardingPassed = getBoolean(IS_ONBOARDING_PASSED, false))
        }

    override fun clearSettings() {
        prefser.remove(IS_ONBOARDING_PASSED)
    }

    companion object {
        private const val IS_ONBOARDING_PASSED = "settings.IS_ONBOARDING_PASSED"
    }
}
