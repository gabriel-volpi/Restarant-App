package com.example.restaurantapptheme.restaurants

import com.example.restaurantapptheme.restaurants.data.remote.RemoteRestaurant
import com.example.restaurantapptheme.restaurants.domain.Restaurant

object DummyContent {
    fun getDomainRestaurants() = arrayListOf(
        Restaurant(0, "title0", "description0", false),
        Restaurant(1, "title1", "description1", false),
        Restaurant(2, "title2", "description2", false),
        Restaurant(3, "title3", "description3", false),
    )

    fun getRemoteRestaurants() = getDomainRestaurants().map {
        RemoteRestaurant(
            id = it.id,
            title = it.title,
            description = it.description
        )
    }
}