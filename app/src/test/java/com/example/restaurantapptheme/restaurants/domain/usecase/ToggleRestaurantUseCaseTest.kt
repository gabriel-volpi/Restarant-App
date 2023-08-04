package com.example.restaurantapptheme.restaurants.domain.usecase

import com.example.restaurantapptheme.FakeApiService
import com.example.restaurantapptheme.FakeRoomDao
import com.example.restaurantapptheme.restaurants.DummyContent
import com.example.restaurantapptheme.restaurants.data.RestaurantsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ToggleRestaurantUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun toggleRestaurant_isUpdatingFavoriteField() = scope.runTest {

        val restaurantsRepository = RestaurantsRepository(
            restInterface = FakeApiService(),
            restaurantsDao = FakeRoomDao(),
            dispatcher = dispatcher
        )
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(
            repository = restaurantsRepository
        )
        val useCase = ToggleRestaurantUseCase(
            repository = restaurantsRepository,
            getSortedRestaurantsUseCase = getSortedRestaurantsUseCase
        )

        restaurantsRepository.loadRestaurants()
        advanceUntilIdle()

        val restaurants = DummyContent.getDomainRestaurants()
        val targetItem = restaurants[0]
        val isFavorite = targetItem.isFavorite
        val updatedRestaurants = useCase(
            id = targetItem.id,
            oldValue = isFavorite
        )

        restaurants[0] = targetItem.copy(isFavorite = !isFavorite)

        assert(updatedRestaurants[0] == restaurants[0])
    }
}