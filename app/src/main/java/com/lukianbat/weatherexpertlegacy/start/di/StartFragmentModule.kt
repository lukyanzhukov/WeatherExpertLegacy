package com.lukianbat.weatherexpertlegacy.start.di

import androidx.lifecycle.ViewModel
import com.lukianbat.core.di.ViewModelKey
import com.lukianbat.weatherexpertlegacy.start.presentation.StartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StartFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindViewModel(viewmodel: StartViewModel): ViewModel
}
