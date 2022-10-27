package com.eloyan.weathereloyan.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eloyan.weathereloyan.R
import com.eloyan.weathereloyan.databinding.ActivityMainBinding
import com.eloyan.weathereloyan.ui.fragments.WeatherFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, WeatherFragment.newInstance())
            .commit()
    }
}