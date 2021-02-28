package com.example.testapp.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_choose_app.*

@AndroidEntryPoint
class ChooseAppFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_ticket.setOnClickListener {

            findNavController().navigate(ChooseAppFragmentDirections.actionButtonFragmentToTicketFragment())
        }
        btn_beer.setOnClickListener {

            findNavController().navigate(ChooseAppFragmentDirections.actionButtonFragmentToBeerFragment())
        }
    }
}