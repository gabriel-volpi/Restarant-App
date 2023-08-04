package com.example.restaurantapptheme.restaurants.domain.usecase

import com.example.restaurantapptheme.restaurants.data.RestaurantsRepository
import com.example.restaurantapptheme.restaurants.domain.model.Restaurant
import javax.inject.Inject

class GetInitialRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {

    suspend operator fun invoke(): List<Restaurant> {
        repository.loadRestaurants()
        return getSortedRestaurantsUseCase()
    }

}