package com.lukianbat.core.common.gateway

import com.lukianbat.core.common.model.Settings

interface SettingsGateway {
    val settings: Settings

    fun saveSettings(settings: Settings)

    fun clearSettings()
}
