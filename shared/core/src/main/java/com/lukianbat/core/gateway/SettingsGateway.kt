package com.lukianbat.core.gateway

import com.lukianbat.core.common.Settings

interface SettingsGateway {
    val settings: Settings

    fun saveSettings(settings: Settings)

    fun clearSettings()
}
