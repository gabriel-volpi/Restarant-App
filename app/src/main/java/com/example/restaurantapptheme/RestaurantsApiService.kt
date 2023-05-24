package com.example.restaurantapptheme

import retrofit2.Call
import retrofit2.http.GET

interface RestaurantsApiService {

    @GET("restaurants.json")
    suspend fun getRestaurants() : List<Restaurant>
}