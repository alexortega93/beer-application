package com.example.testapp.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.data.model.Operation
import com.example.testapp.ui.main.adapter.OperationsAdapter
import com.example.testapp.util.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_operation_ticket.*

@AndroidEntryPoint
class OperationTicketFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_operation_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startParcing()
    }

    private fun startParcing() {
        val myJson: String? = Utils.inputStreamToString(
            requireActivity().resources.openRawResource(
                R.raw.transactions
            )
        )

        val myType = object : TypeToken<List<Operation>>() {}.type
        val operations = Gson().fromJson<List<Operation>>(myJson, myType)
        val resume = Utils.getOperationsMonthly(operations)

        rv_operations.layoutManager = LinearLayoutManager(requireActivity())
        rv_operations.adapter = OperationsAdapter(resume)
    }
}