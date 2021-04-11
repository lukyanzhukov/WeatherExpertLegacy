package com.lukianbat.weatherexpertlegacy.main.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lukianbat.weatherexpertlegacy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()
}