package com.example.restaurantapptheme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private var restInterface: RestaurantsApiService

    val state = mutableStateOf(emptyList<Restaurant>())

    private val errorHandler = CoroutineExceptionHandler { _,exception ->
        exception.printStackTrace()
    }


    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            ).baseUrl(
                "https://restaurantappbe-default-rtdb.firebaseio.com/"
            ).build()

        restInterface = retrofit.create(
            RestaurantsApiService::class.java
        )
        getRestaurants()
    }


    private fun getRestaurants() {
        viewModelScope.launch(errorHandler) {
            val restaurants = getRemoteRestaurants()
            state.value = restaurants.restoreSelections()
        }
    }

    fun toggleFavorite(id: Int) {
        val restaurants = state.value.toMutableList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }
        val item = restaurants[itemIndex]
        restaurants[itemIndex] = item.copy(isFavorite = !item.isFavorite)
        storeSelection(restaurants[itemIndex])
        state.value = restaurants
    }

    private fun storeSelection(item: Restaurant) {
        val savedToggled = stateHandle
            .get<List<Int>?>(FAVORITES)
            .orEmpty().toMutableList()
        if (item.isFavorite) savedToggled.add(item.id)
        else savedToggled.remove(item.id)
        stateHandle[FAVORITES] = savedToggled
    }

    companion object {
        const val FAVORITES = "favorites"
    }

    private fun List<Restaurant>.restoreSelections() : List<Restaurant> {
        stateHandle.get<List<Int>?>(FAVORITES)?.let {selectedIds ->
        val restaurantMap = this.associateBy { it.id }
        selectedIds.forEach { id ->
            restaurantMap[id]?.isFavorite = true
        }
        return restaurantMap.values.toList()
        }
        return this
    }

    private suspend fun getRemoteRestaurants() : List<Restaurant> {
        return withContext(Dispatchers.IO) {
            restInterface.getRestaurants()
        }
    }
}