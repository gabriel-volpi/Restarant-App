package com.example.restaurantapptheme.restaurants.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapptheme.restaurants.data.RestaurantsRepository
import com.example.restaurantapptheme.restaurants.domain.GetInitialRestaurantsUseCase
import com.example.restaurantapptheme.restaurants.domain.ToggleRestaurantUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RestaurantsViewModel() : ViewModel() {

    private val repository = RestaurantsRepository()

    private val getRestaurantsUseCase = GetInitialRestaurantsUseCase()
    private val toggleRestaurantUseCase = ToggleRestaurantUseCase()

    private val _state = mutableStateOf(
        RestaurantScreenState(
            restaurants = listOf(),
            isLoading = true
        )
    )

    val state: State<RestaurantScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _,exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message,
            isLoading = false
        )
    }
    init {
        getRestaurants()
    }
    private fun getRestaurants() {
        viewModelScope.launch(errorHandler) {
            val restaurants = getRestaurantsUseCase()
            _state.value = _state.value.copy(
                restaurants = restaurants,
                isLoading = false
            )
        }
    }

    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedRestaurants = toggleRestaurantUseCase(id, oldValue)
            _state.value = _state.value.copy(restaurants = updatedRestaurants)
        }
    }
}