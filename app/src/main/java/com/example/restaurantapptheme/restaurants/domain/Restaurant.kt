package com.example.restaurantapptheme.restaurants.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Restaurant(
    val id: Int,
    val title: String,
    val description: String,
    val isFavorite: Boolean = false
)