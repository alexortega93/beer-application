package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        findNavController(R.id.nav_host).addOnDestinationChangedListener { controller, destination, arguments ->
            title = when(destination.id) {
                R.id.beer_fragment -> "Cervezas"
                R.id.ticket_fragment -> "Ticket"
                R.id.detail_beer_fragment -> arguments?.getString("beerName","No hubo")
                else -> "TestApp"
            }
        }
    }
}