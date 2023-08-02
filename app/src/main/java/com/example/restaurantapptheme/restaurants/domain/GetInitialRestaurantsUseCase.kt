package com.example.restaurantapptheme.restaurants.domain

import com.example.restaurantapptheme.restaurants.data.RestaurantsRepository

class GetInitialRestaurantsUseCase {

    private val repository: RestaurantsRepository = RestaurantsRepository()
    private val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase()

    suspend operator fun invoke(): List<Restaurant> {
        repository.loadRestaurants()
        return getSortedRestaurantsUseCase()
    }

}