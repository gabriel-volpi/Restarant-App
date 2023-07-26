package com.example.restaurantapptheme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class RestaurantsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private var restInterface: RestaurantsApiService
    private var restaurantsDao = RestaurantsDb.getDaoInstance(
        RestaurantsApplication.getAppContext()
    )

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
            val restaurants = getAllRestaurants()
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
        viewModelScope.launch {
            toggleFavoriteRestaurant(id, item.isFavorite)
        }
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
        val restaurantMap = this.associateBy { it.id }.toMutableMap()
        selectedIds.forEach { id ->
            val restaurant = restaurantMap[id] ?: return@forEach
            restaurantMap[id] = restaurant.copy(isFavorite = true)
        }
        return restaurantMap.values.toList()
        }
        return this
    }

    private suspend fun getAllRestaurants() : List<Restaurant> {
        return withContext(Dispatchers.IO) {
            try {
                val restaurants = restInterface.getRestaurants()
                restaurantsDao.addAll(restaurants)
                return@withContext restaurants
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        restaurantsDao.getAll()
                    }
                    else -> throw e
                }
            }
        }
    }

    private suspend fun toggleFavoriteRestaurant(id: Int, oldValue: Boolean) = withContext(Dispatchers.IO) {
        restaurantsDao.update(
            partialRestaurant = PartialRestaurant(
                id = id,
                isFavorite = !oldValue
            )
        )
    }
}