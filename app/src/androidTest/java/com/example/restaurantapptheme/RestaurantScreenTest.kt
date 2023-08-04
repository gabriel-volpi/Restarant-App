package com.example.restaurantapptheme

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.restaurantapptheme.restaurants.DummyContent
import com.example.restaurantapptheme.restaurants.presentation.Description
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantScreenState
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantsScreen
import com.example.restaurantapptheme.ui.theme.RestaurantAppTheme
import org.junit.Rule
import org.junit.Test

class RestaurantScreenTest {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun initialState_isRendered() {

        val state = RestaurantScreenState(
            restaurants = emptyList(),
            isLoading = true
        )

        testRule.setContent {
            RestaurantAppTheme {
                RestaurantsScreen(
                    state = state,
                    onFavoriteClick = { _: Int, _:Boolean -> },
                    onItemClick = {}
                )
            }
        }
        testRule.onNodeWithContentDescription(
            label = Description.RESTAURANT_LOADING
        ).assertIsDisplayed()
    }

    @Test
    fun stateWithContent_isRendered() {

        val restaurants = DummyContent.getDomainRestaurants()
        val state = RestaurantScreenState(
            restaurants = restaurants,
            isLoading = false
        )

        testRule.setContent {
            RestaurantAppTheme {
                RestaurantsScreen(
                    state = state,
                    onFavoriteClick = { _: Int, _:Boolean -> },
                    onItemClick = {}
                )
            }
        }

        testRule.onNodeWithText(restaurants[0].title).assertIsDisplayed()
        testRule.onNodeWithText(restaurants[0].description).assertIsDisplayed()
        testRule.onNodeWithContentDescription(Description.RESTAURANT_LOADING).assertDoesNotExist()
    }

    @Test
    fun errorState_isRendered() {
        val state = RestaurantScreenState(
            restaurants = emptyList(),
            isLoading = false,
            error = "error message"
        )

        testRule.setContent {
            RestaurantAppTheme {
                RestaurantsScreen(
                    state = state,
                    onFavoriteClick = { _: Int, _:Boolean -> },
                    onItemClick = {}
                )
            }
        }

        testRule.onNodeWithText("error message").assertIsDisplayed()
        testRule.onNodeWithContentDescription(Description.RESTAURANT_LOADING).assertDoesNotExist()
    }

    @Test
    fun stateWithContent_clickOnItem_isRegistered() {
        val restaurants = DummyContent.getDomainRestaurants()
        val targetRestaurant = restaurants[0]
        val state = RestaurantScreenState(
            restaurants = restaurants,
            isLoading = false
        )

        testRule.setContent {
            RestaurantAppTheme {
                RestaurantsScreen(
                    state = state,
                    onFavoriteClick = { _: Int, _:Boolean -> },
                    onItemClick = { id ->
                        assert(id == targetRestaurant.id)
                    }
                )
            }
        }

        testRule.onNodeWithText(targetRestaurant.title).performClick()

    }
}