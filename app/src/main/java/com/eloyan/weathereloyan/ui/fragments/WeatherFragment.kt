package com.eloyan.weathereloyan.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.eloyan.weathereloyan.R
import com.eloyan.weathereloyan.adapters.PeriodViewPagerAdapter
import com.eloyan.weathereloyan.databinding.FragmentWeatherBinding
import com.eloyan.weathereloyan.model.common.RequestResult
import com.eloyan.weathereloyan.model.location.LocationError
import com.eloyan.weathereloyan.utils.isPermissionGranted
import com.eloyan.weathereloyan.utils.showEntryCityDialog
import com.eloyan.weathereloyan.utils.showLocationSettingsDialog
import com.eloyan.weathereloyan.utils.showToast
import com.eloyan.weathereloyan.viewmodel.WeatherViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private lateinit var resultLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentWeatherBinding

    private val weatherViewModel: WeatherViewModel by activityViewModels()
    private val vpFragmentsList =
        listOf(DailyWeatherFragment.newInstance(), WeekWeatherFragment.newInstance())

    companion object {
        private const val TAG = "WeatherFragment"
        @JvmStatic
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        init()
    }

    override fun onResume() {
        super.onResume()

        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION))
        {
            weatherViewModel.getLocation()
        }
    }

    private fun init() {
        setViewPagerAdapter()
        setLiveDataListeners()

        with(binding) {
            imgSync.setOnClickListener {
                weatherViewModel.getLocation()
            }
            imgSearch.setOnClickListener {
                showEntryCityDialog(requireContext())
                { city ->
                    city?.let { city -> weatherViewModel.getWeatherInfo(city) }
                }
            }
        }
    }

    private fun checkPermission(permission: String) {
        if (!isPermissionGranted(permission)) {
            permissionListener()
            resultLauncher.launch(permission)
        }
    }

    private fun permissionListener() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            val message = if (it) {
                requireContext().getString(R.string.granted_permission)
            } else {
                requireContext().getString(R.string.not_granted_permission)
            }
            showToast(requireContext(), message)

            weatherViewModel.getLocation()
        }
    }

    private fun setViewPagerAdapter() = with(binding) {
        vpWeatherList.adapter =
            PeriodViewPagerAdapter(activity as FragmentActivity, vpFragmentsList)

        val weatherPeriodList = listOf(
            requireContext().getString(R.string.day_weather_period),
            requireContext().getString(R.string.week_weather_period)
        )
        TabLayoutMediator(tbWeatherPeriod, vpWeatherList)
        { tab, position ->
            tab.text = weatherPeriodList[position]
        }.attach()
    }

    private fun setLiveDataListeners() = with(binding) {
        weatherViewModel.currentWeatherLiveData.observe(viewLifecycleOwner) {
            tvDate.text = it.date
            tvCity.text = it.city
            tvCurTemperature.text = "${it.currentTemperature}°C"
            tvMaxMinTemp.text = "${it.maxTemperature}°C/${it.minTemperature}°C"
            tvCondition.text = it.conditionText

            Picasso.get().load("https:" + it.conditionImageUrl).into(imgCondition)
        }

        weatherViewModel.progressBarLiveData.observe(viewLifecycleOwner) { isShowProgressBar ->
            if (isShowProgressBar) {
                imgSync.visibility = View.GONE
                syncProgressBar.visibility = View.VISIBLE
            } else {
                imgSync.visibility = View.VISIBLE
                syncProgressBar.visibility = View.GONE
            }
        }

        weatherViewModel.locationCoordinates.observe(viewLifecycleOwner) { Result ->
            when (Result)
            {
                is RequestResult.Success -> {
                    weatherViewModel.getWeatherInfo("${Result.successResponse.latitude},${Result.successResponse.longitude}")
                }
                is RequestResult.Error -> {
                    processLocationRequestError(Result.locationError)
                }
            }
        }
    }

    private fun processLocationRequestError(result: LocationError) {
        if (result== LocationError.PermissionDenied)
        {
            showToast(requireContext(), requireContext().getString(R.string.location_permission_denied))
        }
        else
        {
            showLocationSettingsDialog(requireContext()) {
                requireContext().startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
    }
}