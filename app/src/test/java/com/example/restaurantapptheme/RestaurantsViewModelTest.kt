package com.example.restaurantapptheme

import com.example.restaurantapptheme.restaurants.DummyContent
import com.example.restaurantapptheme.restaurants.data.RestaurantsRepository
import com.example.restaurantapptheme.restaurants.domain.GetInitialRestaurantsUseCase
import com.example.restaurantapptheme.restaurants.domain.GetSortedRestaurantsUseCase
import com.example.restaurantapptheme.restaurants.domain.ToggleRestaurantUseCase
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantScreenState
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantsViewModel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

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
        assert(
            currentState == RestaurantScreenState(
                restaurants = DummyContent.getDomainRestaurants(),
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