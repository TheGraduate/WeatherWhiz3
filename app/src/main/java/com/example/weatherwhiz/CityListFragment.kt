package com.example.weatherwhiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.weatherwhiz.databinding.FragmentCityListBinding

class CityListFragment : Fragment() {

    private var _binding: FragmentCityListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CityListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CityListViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.cityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cityRecyclerView.adapter = CityAdapter()
    }

    private fun observeViewModel() {
        viewModel.cities.observe(viewLifecycleOwner) { cities ->
            (binding.cityRecyclerView.adapter as CityAdapter).submitList(cities)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error) showErrorFragment()
        }
    }

    private fun showErrorFragment() {
        parentFragmentManager.commit {
            replace(R.id.fragment_container, ErrorFragment { viewModel.fetchCities() })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}