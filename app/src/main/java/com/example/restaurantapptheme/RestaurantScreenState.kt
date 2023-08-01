package com.example.restaurantapptheme

import com.example.restaurantapptheme.Restaurant

data class RestaurantScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
