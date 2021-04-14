package com.lukianbat.weatherexpertlegacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lukianbat.coreui.utils.startActivity
import com.lukianbat.weatherexpertlegacy.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<MainActivity>()
        finish()
    }
}
