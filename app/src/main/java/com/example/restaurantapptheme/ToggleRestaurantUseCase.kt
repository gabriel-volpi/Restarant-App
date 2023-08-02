package com.example.restaurantapptheme

class ToggleRestaurantUseCase {

    private val repository: RestaurantsRepository = RestaurantsRepository()

    suspend operator fun invoke(
        id: Int,
        oldValue: Boolean
    ): List<Restaurant> {
        val newFav = oldValue.not()
        repository.toggleFavoriteRestaurant(id, newFav)
        return GetRestaurantsUseCase().invoke()
    }
}