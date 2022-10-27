package com.eloyan.weathereloyan.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eloyan.weathereloyan.R
import com.eloyan.weathereloyan.adapters.WeatherListAdapter
import com.eloyan.weathereloyan.databinding.FragmentWeekWeatherBinding
import com.eloyan.weathereloyan.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeekWeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeekWeatherBinding
    private lateinit var adapter: WeatherListAdapter
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeekWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView()
        setLiveDataListeners()
    }

    private fun initRecycleView() = with(binding) {
        adapter = WeatherListAdapter()
        rvWeekWeatherList.layoutManager = LinearLayoutManager(activity)
        rvWeekWeatherList.swapAdapter(adapter, true)
    }

    private fun setLiveDataListeners() {
        weatherViewModel.weekWeatherLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeekWeatherFragment()
    }
}