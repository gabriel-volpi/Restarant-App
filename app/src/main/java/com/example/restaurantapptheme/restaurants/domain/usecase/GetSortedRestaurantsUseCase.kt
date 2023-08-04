package com.example.restaurantapptheme.restaurants.domain.usecase

import com.example.restaurantapptheme.restaurants.data.RestaurantsRepository
import com.example.restaurantapptheme.restaurants.domain.model.Restaurant
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {

    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants().sortedBy { it.title }
    }

}