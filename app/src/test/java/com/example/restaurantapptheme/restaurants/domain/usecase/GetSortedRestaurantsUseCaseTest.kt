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
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSortedRestaurantsUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun getSortedRestaurants_returnRestaurantList() = scope.runTest {
        val restaurantsRepository = RestaurantsRepository(
            restInterface = FakeApiService(),
            restaurantsDao = FakeRoomDao(),
            dispatcher = dispatcher
        )

        val useCase = GetSortedRestaurantsUseCase(
            repository = restaurantsRepository
        )

        restaurantsRepository.loadRestaurants()
        advanceUntilIdle()

        val actual = useCase.invoke()
        val expected = DummyContent.getDomainRestaurants().sortedBy { it.title }

        assertEquals(expected, actual)
    }
}