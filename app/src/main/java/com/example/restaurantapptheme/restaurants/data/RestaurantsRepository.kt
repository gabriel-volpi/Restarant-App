package com.example.restaurantapptheme.restaurants.data

import com.example.restaurantapptheme.restaurants.data.di.IoDispatcher
import com.example.restaurantapptheme.restaurants.domain.model.Restaurant
import com.example.restaurantapptheme.restaurants.data.local.LocalRestaurant
import com.example.restaurantapptheme.restaurants.data.local.PartialLocalRestaurant
import com.example.restaurantapptheme.restaurants.data.local.RestaurantsDao
import com.example.restaurantapptheme.restaurants.data.remote.RestaurantsApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

private const val ERROR_MESSAGE = "Something is wrong. We have no data. Please check yout internet" +
        " connection"

@Singleton
class RestaurantsRepository @Inject constructor(
    private val restInterface: RestaurantsApiService,
    private val restaurantsDao: RestaurantsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun loadRestaurants() {
        return withContext(dispatcher) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty()) throw Exception(ERROR_MESSAGE)
                    }
                    else -> throw e
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favoriteRestaurants = restaurantsDao.getAllFavorited()

        restaurantsDao.addAll(
            remoteRestaurants.map {
                LocalRestaurant(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isFavorite = false
                )
            }
        )
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialLocalRestaurant(
                    id = it.id,
                    isFavorite = true
                )
            }
        )
    }

    suspend fun toggleFavoriteRestaurant(id: Int, value: Boolean) = withContext(dispatcher) {
        restaurantsDao.update(
            partialRestaurant = PartialLocalRestaurant(
                id = id,
                isFavorite = value
            )
        )
    }

    suspend fun getRestaurants() : List<Restaurant> {
        return withContext(dispatcher) {
            return@withContext restaurantsDao.getAll().map {
                Restaurant(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isFavorite = it.isFavorite
                )
            }
        }
    }
}