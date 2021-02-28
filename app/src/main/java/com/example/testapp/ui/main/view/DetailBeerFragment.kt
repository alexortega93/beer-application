package com.example.testapp.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.testapp.R
import com.example.testapp.ui.main.adapter.BeerDetailAdapter
import com.example.testapp.ui.main.viewmodel.BeerDetailViewModel
import com.example.testapp.util.Status
import com.example.testapp.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_beer_detail.*

@AndroidEntryPoint
class DetailBeerFragment: Fragment() {

    private val viewModel: BeerDetailViewModel by viewModels()
    private val args: DetailBeerFragmentArgs by navArgs()
    private lateinit var adapter: BeerDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beer_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.start(args.beerId)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        adapter = BeerDetailAdapter()
        rv_food_pairing.layoutManager = LinearLayoutManager(requireContext())
        rv_food_pairing.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.id.observe( viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { entity ->
                        val date = entity.first_brewed.split("/")
                        Glide.with(this)
                                .load(entity.image_url)
                                .transform(CircleCrop())
                                .into(iv_beer_image)
                        tv_beer_tagline.text = entity.tagline
                        tv_beer_description.text = entity.description
                        tv_beer_date.text = String.format(resources.getString(R.string.beer_date_text),Utils.getMonthName(date[0].toInt().minus(1)), date[1])
                        adapter.addFoodPairings(entity.food_pairing.split(":"))
                    }
                }
                Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Status.LOADING -> {

                }
            }
        })
    }
}