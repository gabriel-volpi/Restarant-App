package com.example.restaurantapptheme.restaurants.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapptheme.restaurants.data.di.MainDispatcher
import com.example.restaurantapptheme.restaurants.domain.usecase.GetInitialRestaurantsUseCase
import com.example.restaurantapptheme.restaurants.domain.usecase.ToggleRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetInitialRestaurantsUseCase,
    private val toggleRestaurantUseCase: ToggleRestaurantUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _state: MutableStateFlow<RestaurantScreenState> = MutableStateFlow(
        RestaurantScreenState.LoadingState
    )

    val state: MutableStateFlow<RestaurantScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _,exception ->
        exception.printStackTrace()
        _state.value = RestaurantScreenState.ErrorState
    }
    init {
        getRestaurants()
    }
    fun getRestaurants() {
        _state.value = RestaurantScreenState.LoadingState
        viewModelScope.launch(errorHandler + dispatcher) {
            val restaurants = getRestaurantsUseCase()
            _state.emit(RestaurantScreenState.IdleState(restaurants))
        }
    }

    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler + dispatcher) {
            val updatedRestaurants = toggleRestaurantUseCase(id, oldValue)
            _state.emit(RestaurantScreenState.IdleState(updatedRestaurants))
        }
    }
}