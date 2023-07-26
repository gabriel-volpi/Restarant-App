package com.example.restaurantapptheme

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey()
    @ColumnInfo(name = "r_id")
    @SerializedName("r_id")
    val id: Int,
    @ColumnInfo(name = "r_title")
    @SerializedName("r_title")
    val title: String,
    @ColumnInfo(name = "r_description")
    @SerializedName("r_description")
    val description: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)

//não está mais sendo usado porque estou usando o banco de dados com retrofit
val dummyRestaurants = listOf(
    Restaurant(0, "Alfredo's dishes", "At Alfredo's, we provide the best seafood dishes."),
    Restaurant(1, "Jamie's burgers", "At Jamie's, we serve the best meat and vegan burgers!"),
    Restaurant(2, "Pizza John", "Get the best pizza in town. We also serve vegan burgers!"),
    Restaurant(3, "Dinner in the clouds", "At DitC, you can experience the full immersive dining experience." ),
    Restaurant(4, "Eternity lunch", "At Eternity lunch, you will get the best American dishes."),
    Restaurant(5, "Food and coffee", "Drink your coffee and then have lunch at FaC!"),
    Restaurant(6, "Pizza and burgers Brazil", "Get your best burgers and pizza here in Brazil!"),
    Restaurant(7, "Merry Dinner", "Get the Christmas experience at Merry Dinner with Santa!"),
    Restaurant(8, "Uncle Ben's Pizza shop", "Relieve the childhood pizza experience at Uncle Ben's pizza shop, now!"),
    Restaurant(9, "Spicy Grill Toronto", "Get the best culinary experience in Toronto."),
    Restaurant(10, "Cheese Food shop", "Cheesy meals with cheesy ingredients, it's all about cheese!"),
    Restaurant(11, "Mexican spicy Food in Atlanta", "Get your spicy food dose here in Atlanta at Mexican spicy Food!"),
    Restaurant(12, "Spanish Kitchen reinvented", "Check out the true culinary experience with spanish dishes in NYC!"),
    Restaurant(13, "Mike and Ben's food pub", "Come get the best food in New Jersey, now at Mike and Ben's!"),
)