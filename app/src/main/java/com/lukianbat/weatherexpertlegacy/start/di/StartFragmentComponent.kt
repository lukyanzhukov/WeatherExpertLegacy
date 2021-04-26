package com.lukianbat.weatherexpertlegacy.start.di

import com.lukianbat.weatherexpertlegacy.start.presentation.StartFragment
import dagger.Subcomponent

@Subcomponent(modules = [StartFragmentModule::class])
interface StartFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): StartFragmentComponent
    }

    fun inject(fragment: StartFragment)
}
