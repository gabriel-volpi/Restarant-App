package com.example.restaurantapptheme.restaurants.presentation.list

import com.example.restaurantapptheme.FakeApiService
import com.example.restaurantapptheme.FakeRoomDao
import com.example.restaurantapptheme.restaurants.DummyContent
import com.example.restaurantapptheme.restaurants.data.RestaurantsRepository
import com.example.restaurantapptheme.restaurants.domain.usecase.GetInitialRestaurantsUseCase
import com.example.restaurantapptheme.restaurants.domain.usecase.GetSortedRestaurantsUseCase
import com.example.restaurantapptheme.restaurants.domain.usecase.ToggleRestaurantUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class RestaurantsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun initialState_isProduced() = scope.runTest {
        val viewModel = getViewModel()
        val initialState = viewModel.state.value
        assert(
            initialState == RestaurantScreenState(
                restaurants = emptyList(),
                isLoading = true,
                error = null
            )
        )
    }

    @Test
    fun stateWithContent_isProduced() = scope.runTest {
        val viewModel = getViewModel()
        advanceUntilIdle()
        val currentState = viewModel.state.value
        val expectedRestaurants = DummyContent.getDomainRestaurants().sortedBy { it.title }
        assert(
            currentState == RestaurantScreenState(
                restaurants = expectedRestaurants,
                isLoading = false,
                error = null
            )
        )
    }


    private fun getViewModel(): RestaurantsViewModel {
        val restaurantsRepository = RestaurantsRepository(
            restInterface = FakeApiService(),
            restaurantsDao = FakeRoomDao(),
            dispatcher = dispatcher
        )
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(
            repository = restaurantsRepository
        )
        val getInitialRestaurantsUseCase = GetInitialRestaurantsUseCase(
            repository = restaurantsRepository,
            getSortedRestaurantsUseCase = getSortedRestaurantsUseCase
        )
        val toggleRestaurantUseCase = ToggleRestaurantUseCase(
            repository = restaurantsRepository,
            getSortedRestaurantsUseCase = getSortedRestaurantsUseCase
        )
        return RestaurantsViewModel(
            getRestaurantsUseCase = getInitialRestaurantsUseCase,
            toggleRestaurantUseCase = toggleRestaurantUseCase,
            dispatcher = dispatcher
        )
    }
}