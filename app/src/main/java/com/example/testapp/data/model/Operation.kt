package com.example.testapp.data.model

data class Operation(
    val uuid: Long = 0,
    val description: String = "",
    val category: String = "",
    val operation: String = "",
    val amount: Double = 0.0,
    val status: String = "",
    val creation_date: String = ""
)