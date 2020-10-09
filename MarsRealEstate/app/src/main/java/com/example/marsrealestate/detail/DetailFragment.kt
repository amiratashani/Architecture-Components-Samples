package com.example.marsrealestate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.marsrealestate.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private val args by navArgs<DetailFragmentArgs>()
    private val detailViewModel by lazy {
        val detailViewModelFactory =
            DetailViewModelFactory(args.selectedProperty, requireActivity().application)
        ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.viewModel = detailViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}