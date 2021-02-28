package com.example.testapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "world_beer")
data class BeerEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val image_url: String,
    val tagline: String,
    val description: String,
    val first_brewed: String,
    val food_pairing: String
)