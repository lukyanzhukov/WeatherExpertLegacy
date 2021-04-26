package com.lukianbat.feature.onboarding.presentation.di

import com.lukianbat.feature.onboarding.presentation.presentation.OnboardingFragment
import dagger.Subcomponent

@Subcomponent(modules = [OnboardingModule::class])
interface OnboardingComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): OnboardingComponent
    }

    fun inject(fragment: OnboardingFragment)
}
