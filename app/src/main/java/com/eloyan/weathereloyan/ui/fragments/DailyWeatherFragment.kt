package com.eloyan.weathereloyan.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eloyan.weathereloyan.adapters.WeatherListAdapter
import com.eloyan.weathereloyan.databinding.FragmentDailyWeatherBinding
import com.eloyan.weathereloyan.model.weather.data.WeatherData
import com.eloyan.weathereloyan.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject

@AndroidEntryPoint
class DailyWeatherFragment : Fragment() {
    private lateinit var binding: FragmentDailyWeatherBinding
    private lateinit var adapter: WeatherListAdapter

    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        setLiveDataListeners()
    }

    private fun initRecycleView() = with(binding)
    {
        rvDailyWeatherList.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherListAdapter()
        rvDailyWeatherList.setRecycledViewPool(RecyclerView.RecycledViewPool())

        rvDailyWeatherList.swapAdapter(adapter, true)
    }

    private fun setLiveDataListeners() {
        weatherViewModel.dailyWeatherLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DailyWeatherFragment()
    }
}