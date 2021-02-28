package com.example.testapp.util

import com.example.testapp.data.entity.BeerEntity
import com.example.testapp.data.model.BeerResponse
import com.example.testapp.data.model.Operation
import java.io.IOException
import java.io.InputStream
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Utils {

    data class OperationsMonthly(
        val month: Int = 0,
        val pendingOperation: Int = 0,
        val blockedOperation: Int = 0,
        val moneyEarn: Double = 0.0,
        val moneySpend: Double = 0.0,
        val listCategories: List<Categories> = ArrayList()
    )

    data class Categories(
        val name: String = "",
        val percentage: Double = 0.0
    )

    enum class STATUS(val status: String) {
        REJECTED("rejected"),
        PENDING("pending"),
        DONE("done")
    }

    enum class OPERATION(val operationType: String) {
        IN("in"),
        OUT("out")
    }

    companion object {

        fun getOperationsMonthly(operations: List<Operation>): ArrayList<OperationsMonthly> {

            val operationsMonthly: ArrayList<OperationsMonthly> = ArrayList()

            val format = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val calendar = GregorianCalendar.getInstance()

            val groups = operations.groupBy { item ->
                val date = format.parse(item.creation_date)
                calendar.time = date!!
                calendar.get(Calendar.MONTH)
            }
            for(position in 0..11) {
                val operationsGrouped = groups[position]
                val moneySpend = operationsGrouped!!.sumByDouble { if(it.status == STATUS.DONE.status && it.operation == OPERATION.OUT.operationType) { it.amount } else { 0.0 } }
                operationsMonthly.add(
                    OperationsMonthly( position,
                        operationsGrouped.count { it.status == STATUS.PENDING.status },
                        operationsGrouped.count { it.status == STATUS.REJECTED.status },
                        operationsGrouped.sumByDouble { if(it.status == STATUS.DONE.status && it.operation == OPERATION.IN.operationType) { it.amount } else { 0.0 } },
                        moneySpend,
                        getCategories(operationsGrouped.filter { it.status == STATUS.DONE.status && it.operation == OPERATION.OUT.operationType }, moneySpend)
                    )
                )
            }
            return operationsMonthly
        }

        fun getMonthName(position: Int): String {
            return DateFormatSymbols().months[position].capitalize(Locale.ROOT)
        }

        private fun getCategories(operationsFilter: List<Operation>, moneySpend: Double): List<Categories> {
            val categories = ArrayList<Categories>()

            val categoriesGrouped = operationsFilter.groupBy { item ->
                item.category
            }
            categoriesGrouped.forEach { (key, value) ->
                categories.add(
                    Categories(
                    key,
                    value.sumByDouble { it.amount.div(moneySpend).times(100) }
                )
                )
            }
            return categories.sortedWith(compareBy { it.percentage }).reversed()
        }

        fun inputStreamToString(inputStream: InputStream): String? {
            return try {
                val bytes = ByteArray(inputStream.available())
                inputStream.read(bytes, 0, bytes.size)
                String(bytes)
            } catch (e: IOException) {
                null
            }
        }

        fun fillTitleWithSpaces(initialString: String, finalLenght: Int): String {
            return initialString.padEnd(finalLenght, ' ')
        }

        fun fillNumberWithSpaces(initialString: String, finalLenght: Int, initialCharacter: String): String {
            return "$initialCharacter${initialString.padStart((finalLenght - initialCharacter.length), ' ')}"
        }

        fun convertResponseToEntity(beerResponse: List<BeerResponse>): List<BeerEntity> {
            val entities = ArrayList<BeerEntity>()

            beerResponse.forEach{ entities.add(
                BeerEntity(
                    it.id,
                    it.name,
                    it.imageUrl,
                    it.tagline,
                    it.description,
                    it.firstBrewed,
                    it.foodPairing.joinToString(separator = ":")
                )
            )}
            return entities
        }
    }
}