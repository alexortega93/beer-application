package com.example.testapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.testapp.R
import com.example.testapp.util.Utils
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(private val items: List<Utils.Categories>): Adapter<CategoryAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Utils.Categories) = with(itemView) {
            tv_category_name.text = Utils.fillTitleWithSpaces(item.name, 17)
            tv_category_percentage.text = Utils.fillNumberWithSpaces(String.format("%.2f", item.percentage),6,"%")
        }
    }
}