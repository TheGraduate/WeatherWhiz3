package com.example.weatherwhiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherwhiz.R
import com.example.weatherwhiz.databinding.FragmentWeatherBinding
import com.example.weatherwhiz.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        //binding.lifecycleOwner = this

        observeViewModel()

        // Assuming lat and lon are passed as arguments
        val lat = arguments?.getDouble("lat")
        val lon = arguments?.getDouble("lon")

        if (lat != null && lon != null) {
            viewModel.fetchWeather(lat, lon)
        } else {
            showErrorFragment()
        }

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.weather.observe(viewLifecycleOwner, Observer { weather ->
            if (weather != null) {
                updateUI(weather)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error) showErrorFragment()
        })
    }

    private fun updateUI(weather: WeatherResponse) {
        binding.tempTextView.text = "${weather.main.temp}°C"
        binding.cityTextView.text = weather.name
    }

    private fun showErrorFragment() {
        parentFragmentManager.commit {
            replace(R.id.fragment_container, ErrorFragment {
                // Assuming lat and lon are passed as arguments
                val lat = arguments?.getDouble("lat")
                val lon = arguments?.getDouble("lon")
                if (lat != null && lon != null) {
                    viewModel.fetchWeather(lat, lon)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}