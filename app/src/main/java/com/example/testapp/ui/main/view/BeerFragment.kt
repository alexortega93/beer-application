package com.example.testapp.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.ui.main.adapter.BeerAdapter
import com.example.testapp.ui.main.viewmodel.BeerViewModel
import com.example.testapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_beer.*

@AndroidEntryPoint
class BeerFragment: Fragment() {

    private lateinit var adapter: BeerAdapter
    private val viewModel: BeerViewModel by viewModels()
    private var currentPage: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.start(currentPage)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        adapter = BeerAdapter { name, id ->
            findNavController().navigate(BeerFragmentDirections.beerFragmentToDetailBeerFragment(name, id))
            currentPage--
            viewModel.beer.removeObservers(viewLifecycleOwner)
        }
        rv_beer.layoutManager = LinearLayoutManager(requireContext())
        rv_beer.adapter = adapter
        srl_beer.setOnRefreshListener {

            viewModel.start(currentPage)
            viewModel.beer
            //Toast.makeText(activity, "Refresh", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {

        viewModel.beer.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    srl_beer.isRefreshing = false
                    if (!it.data.isNullOrEmpty()) {
                         if(((it.data.size / 20) + 1) != currentPage) {
                             currentPage = ((it.data.size / 20) + 1)
                        }
                        adapter.setBeers(ArrayList(it.data))
                    }
                }
                Status.ERROR -> {
                    srl_beer.isRefreshing = false
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }

}