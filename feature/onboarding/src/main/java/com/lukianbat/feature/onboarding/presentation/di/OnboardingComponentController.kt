package com.lukianbat.feature.onboarding.presentation.di

interface OnboardingComponentController {
    fun provideOnboardingComponent(): OnboardingComponent

    fun clearOnboardingComponent()
}
