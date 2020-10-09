package com.example.androidtrivia

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.androidtrivia.databinding.FragmentTitleBinding
import kotlinx.android.synthetic.main.fragment_title.*


class TitleFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding =
            DataBindingUtil.inflate<FragmentTitleBinding>(
                inflater,
                R.layout.fragment_title,
                container,
                false
            )

        setHasOptionsMenu(true)

        binding.playButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment))
//        {
//            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
//            it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
//        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,findNavController())
                || super.onOptionsItemSelected(item)
    }
}