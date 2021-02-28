package com.example.testapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.testapp.R
import com.example.testapp.util.Utils
import kotlinx.android.synthetic.main.item_montlhy_operation.view.*

class OperationsAdapter(private val items: ArrayList<Utils.OperationsMonthly>): Adapter<OperationsAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_montlhy_operation, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Utils.OperationsMonthly) = with(itemView) {

            tv_month.text = Utils.getMonthName(item.month)

            tv_pending.text = String.format(resources.getString(R.string.operations_pending), item.pendingOperation)
            tv_blocked.text = String.format(resources.getString(R.string.operations_rejected), item.blockedOperation)
            tv_income.text = Utils.fillNumberWithSpaces("${String.format("%.2f", item.moneyEarn)} ingresos",19,"$")
            tv_spend.text = Utils.fillNumberWithSpaces("${String.format("%.2f", item.moneySpend)} gastos",17,"$")

            rv_categories.layoutManager = LinearLayoutManager(context)
            rv_categories.adapter = CategoryAdapter(item.listCategories)
        }
    }
}