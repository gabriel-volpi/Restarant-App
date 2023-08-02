package com.example.restaurantapptheme

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class RestaurantsRepository {

    private var restInterface: RestaurantsApiService = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        ).baseUrl(
            "https://restaurantappbe-default-rtdb.firebaseio.com/"
        ).build().create(
            RestaurantsApiService::class.java
        )

    private var restaurantsDao = RestaurantsDb.getDaoInstance(
        RestaurantsApplication.getAppContext()
    )

    suspend fun getAllRestaurants() : List<Restaurant> {
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

    suspend fun toggleFavoriteRestaurant(id: Int, value: Boolean) = withContext(
        Dispatchers.IO) {
        restaurantsDao.update(
            partialRestaurant = PartialRestaurant(
                id = id,
                isFavorite = value
            )
        )
    }

}