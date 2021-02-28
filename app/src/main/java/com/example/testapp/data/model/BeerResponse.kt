package com.example.testapp.data.model

import com.google.gson.annotations.SerializedName

data class BeerResponse(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("tagline")
    val tagline: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("first_brewed")
    val firstBrewed: String = "",
    @SerializedName("food_pairing")
    val foodPairing: List<String> = ArrayList(),
    @SerializedName("id")
    val id: String = ""
)