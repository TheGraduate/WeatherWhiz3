package com.example.weatherwhiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherwhiz.databinding.FragmentErrorBinding

class ErrorFragment(private val retryCallback: () -> Unit) : Fragment() {

    private var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)

        binding.retryButton.setOnClickListener {
            retryCallback()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}