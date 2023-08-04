package com.example.restaurantapptheme

import com.example.restaurantapptheme.restaurants.DummyContent
import com.example.restaurantapptheme.restaurants.data.remote.RemoteRestaurant
import com.example.restaurantapptheme.restaurants.data.remote.RestaurantsApiService

class FakeApiService : RestaurantsApiService {

    override suspend fun getRestaurants(): List<RemoteRestaurant> {
        return DummyContent.getRemoteRestaurants()
    }

    override suspend fun getRestaurant(id: Int): Map<String, RemoteRestaurant> {
        TODO("Not yet implemented")
    }
}