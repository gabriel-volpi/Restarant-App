package com.example.restaurantapptheme.restaurants.presentation.list

import com.example.restaurantapptheme.restaurants.domain.Restaurant

data class RestaurantScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
