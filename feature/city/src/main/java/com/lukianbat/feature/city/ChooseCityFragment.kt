package com.lukianbat.feature.city

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class ChooseCityFragment: Fragment(R.layout.fragment_choose_city) {

    private val viewModel by viewModels<ChooseCityViewModel>()
}