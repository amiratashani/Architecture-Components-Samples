package com.example.guessit.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe

import androidx.navigation.fragment.NavHostFragment

import com.example.guessit.R
import com.example.guessit.databinding.FragmentGameBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private val viewModel: GameViewModel by lazy {
         ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
    }

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

//           viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

//        viewModel.score.observe(viewLifecycleOwner) { newScore ->
//            binding.scoreText.text = newScore.toString()
//        }

//        viewModel.word.observe(viewLifecycleOwner) {
//            binding.wordText.text = it
//        }

//        viewModel.currentTime.observe(viewLifecycleOwner) { newScore ->
//            binding.timerText.text = DateUtils.formatElapsedTime(newScore)
//        }

        viewModel.eventGameFinish.observe(viewLifecycleOwner) {
            if (it) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        }

        viewModel.eventBuzz.observe(viewLifecycleOwner) { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        }

        return binding.root

    }

    private fun gameFinished() {
        val action =
            GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value ?: 0)
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = requireActivity().getSystemService<Vibrator>()
        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

}
