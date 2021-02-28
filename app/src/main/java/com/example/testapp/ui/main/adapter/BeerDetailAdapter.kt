package com.example.testapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import kotlinx.android.synthetic.main.item_food_pairing.view.*

class BeerDetailAdapter: RecyclerView.Adapter<BeerDetailAdapter.ViewHolder>()  {

    private var foodPairing: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_food_pairing, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(foodPairing[position])

    override fun getItemCount() = foodPairing.size

    fun addFoodPairings(foodPairings: List<String>) {
        this.foodPairing.clear()
        this.foodPairing.addAll(foodPairings)
        notifyDataSetChanged()
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) = with(itemView) {
            tv_food_pairing.text = String.format(resources.getString(R.string.beer_food_pairing_text), item)
        }
    }
}