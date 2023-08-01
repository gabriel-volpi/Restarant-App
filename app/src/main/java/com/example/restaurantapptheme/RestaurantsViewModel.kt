package com.example.restaurantapptheme

import androidx.compose.runtime.mutableStateOf
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

class RestaurantsViewModel() : ViewModel() {

    private var restInterface: RestaurantsApiService
    private var restaurantsDao = RestaurantsDb.getDaoInstance(
        RestaurantsApplication.getAppContext()
    )

    val state = mutableStateOf(
        RestaurantScreenState(
            restaurants = listOf(),
            isLoading = true
        )
    )

    private val errorHandler = CoroutineExceptionHandler { _,exception ->
        exception.printStackTrace()
        state.value = state.value.copy(
            error = exception.message,
            isLoading = false
        )
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
            state.value = state.value.copy(
                restaurants = restaurants,
                isLoading = false
            )
        }
    }

    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedRestaurants = toggleFavoriteRestaurant(id, oldValue)
            state.value = state.value.copy(restaurants = updatedRestaurants)
        }
    }

    private suspend fun getAllRestaurants() : List<Restaurant> {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty()) throw Exception("Something is wrong." +
                                "We have no data.")
                    }
                    else -> throw e
                }
            }
            return@withContext restaurantsDao.getAll()
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favoriteRestaurants = restaurantsDao.getAllFavorited()

        restaurantsDao.addAll(remoteRestaurants)
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialRestaurant(it.id, true)
            }
        )
    }
    private suspend fun toggleFavoriteRestaurant(id: Int, oldValue: Boolean) = withContext(Dispatchers.IO) {
        restaurantsDao.update(
            partialRestaurant = PartialRestaurant(
                id = id,
                isFavorite = !oldValue
            )
        )
        restaurantsDao.getAll()
    }
}