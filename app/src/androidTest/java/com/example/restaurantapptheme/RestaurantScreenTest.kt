package com.example.restaurantapptheme

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.restaurantapptheme.restaurants.DummyContent
import com.example.restaurantapptheme.restaurants.data.ERROR_MESSAGE
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

        val state = RestaurantScreenState.LoadingState

        testRule.setContent {
            RestaurantAppTheme {
                RestaurantsScreen(
                    state = state,
                    onFavoriteClick = { _: Int, _:Boolean -> },
                    onItemClick = {},
                    onTryAgainClick = {}
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
        val state = RestaurantScreenState.IdleState(restaurants)

        testRule.setContent {
            RestaurantAppTheme {
                RestaurantsScreen(
                    state = state,
                    onFavoriteClick = { _: Int, _:Boolean -> },
                    onItemClick = {},
                    onTryAgainClick = {}
                )
            }
        }

        testRule.onNodeWithText(restaurants[0].title).assertIsDisplayed()
        testRule.onNodeWithText(restaurants[0].description).assertIsDisplayed()
        testRule.onNodeWithContentDescription(Description.RESTAURANT_LOADING).assertDoesNotExist()
    }

    @Test
    fun errorState_isRendered() {
        val state = RestaurantScreenState.ErrorState

        testRule.setContent {
            RestaurantAppTheme {
                RestaurantsScreen(
                    state = state,
                    onFavoriteClick = { _: Int, _:Boolean -> },
                    onItemClick = {},
                    onTryAgainClick = {}
                )
            }
        }

        testRule.onNodeWithText(ERROR_MESSAGE).assertIsDisplayed()
        testRule.onNodeWithContentDescription(Description.RESTAURANT_LOADING).assertDoesNotExist()
    }

    @Test
    fun stateWithContent_clickOnItem_isRegistered() {
        val restaurants = DummyContent.getDomainRestaurants()
        val targetRestaurant = restaurants[0]
        val state = RestaurantScreenState.IdleState(restaurants)

        testRule.setContent {
            RestaurantAppTheme {
                RestaurantsScreen(
                    state = state,
                    onFavoriteClick = { _: Int, _:Boolean -> },
                    onItemClick = { id ->
                        assert(id == targetRestaurant.id)
                    },
                    onTryAgainClick = {}
                )
            }
        }

        testRule.onNodeWithText(targetRestaurant.title).performClick()
    }
}