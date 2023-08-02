package com.example.restaurantapptheme.restaurants.domain

import com.example.restaurantapptheme.restaurants.data.RestaurantsRepository

class GetSortedRestaurantsUseCase {

    private val repository: RestaurantsRepository = RestaurantsRepository()

    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants().sortedBy { it.title }
    }

}