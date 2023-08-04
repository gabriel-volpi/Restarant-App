package com.example.restaurantapptheme.restaurants.presentation.list

import com.example.restaurantapptheme.restaurants.domain.model.Restaurant

//sealed class RestaurantScreenState{
//    object LoadingState: RestaurantScreenState()
//
//    object ErrorState: RestaurantScreenState()
//
//    data class IdleState(val restaurants: List<Restaurant>)
//}

data class RestaurantScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
