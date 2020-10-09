package com.example.androidtrivia

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.androidtrivia.databinding.FragmentGameWonBinding
import java.util.zip.Inflater


class GameWonFragment : Fragment() {
    private lateinit var args: GameWonFragmentArgs
    private lateinit var binding: FragmentGameWonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentGameWonBinding>(
            layoutInflater,
            R.layout.fragment_game_won,
            container,
            false
        )
        args = GameWonFragmentArgs.fromBundle(requireArguments())

        setHasOptionsMenu(true)

        setListener()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager))
            menu.findItem(R.id.share).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListener() {
        binding.nextMatchButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }
    }

    // Creating our Share Intent
    private fun getShareIntent(): Intent {
//        val shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent.type = "text/plain"
//        shareIntent.putExtra(
//            Intent.EXTRA_TEXT,
//            getString(R.string.share_success_text, args.numCorrect, args.numQuestions)
//        )
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
            .setType("text/plain")
            .intent

        return shareIntent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }


}