package com.lukianbat.feature.city.di

import com.lukianbat.feature.city.di.modules.CityDomainModule
import com.lukianbat.feature.city.di.modules.CityModule
import com.lukianbat.feature.city.di.modules.DatabaseModule
import com.lukianbat.feature.city.di.modules.NetworkModule
import com.lukianbat.feature.city.presentation.ChooseCityFragment
import dagger.Subcomponent

@Subcomponent(modules = [DatabaseModule::class, NetworkModule::class, CityDomainModule::class, CityModule::class])
interface CityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CityComponent
    }

    fun inject(fragment: ChooseCityFragment)
}
