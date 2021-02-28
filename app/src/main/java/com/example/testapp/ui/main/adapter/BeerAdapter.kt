package com.example.testapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.testapp.R
import com.example.testapp.data.entity.BeerEntity
import kotlinx.android.synthetic.main.item_beer.view.*

class BeerAdapter(private val listener: (String, String) -> Unit): Adapter<BeerAdapter.ViewHolder>()  {

    private var beers: ArrayList<BeerEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(beers[position], listener)

    override fun getItemCount() = beers.size

    fun setBeers(beers: ArrayList<BeerEntity>) {
        this.beers.clear()
        this.beers.addAll(beers)
        this.beers = ArrayList(this.beers.sortedBy { -it.id.toInt() })
        notifyDataSetChanged()
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BeerEntity, listener: (String, String) -> Unit) = with(itemView) {
            Glide.with(this)
                .load(item.image_url)
                .transform(CircleCrop())
                .into(iv_beer_image)

            tv_beer_name.text = item.name
            tv_beer_tagline.text = item.tagline

            this.setOnClickListener { listener(item.name, item.id) }
        }
    }
}