package com.example.guessit.screens.score

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guessit.R
import com.example.guessit.databinding.FragmentScoreBinding
import kotlin.math.log


/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {
    private val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
    private val viewModel: ScoreViewModel by viewModels {
        ScoreViewModelFactory(scoreFragmentArgs.score)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentScoreBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_score,
            container,
            false
        )
        binding.scoreViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }


}
