package com.example.restaurantapptheme.restaurants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.restaurantapptheme.restaurants.presentation.details.RestaurantDetailsScreen
import com.example.restaurantapptheme.restaurants.presentation.details.RestaurantDetailsViewModel
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantScreenState
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantsScreen
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantsViewModel
import com.example.restaurantapptheme.ui.theme.RestaurantAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantAppTheme {
                RestaurantApp()
            }
        }
    }
}

@Composable
private fun RestaurantApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "restaurants"
    ) {
        composable(route = "restaurants") {
            val viewModel: RestaurantsViewModel = hiltViewModel()
            RestaurantsScreen(
                state = viewModel.state.value,
                onItemClick = { id ->
                    navController.navigate("restaurants/$id")
                },
                onFavoriteClick = { id, oldValue ->
                    viewModel.toggleFavorite(id, oldValue)
                },
                onTryAgainClick = {
                    viewModel.getRestaurants()
                    navController.navigate("restaurants")
                }
            )
        }
        composable(
            route = "restaurants/{restaurant_id}",
            arguments = listOf(navArgument("restaurant_id") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "www.restaurantsapp.details.com/{restaurant_id}" }
            )
        ) {
            val viewModel: RestaurantDetailsViewModel = hiltViewModel()

            RestaurantDetailsScreen(viewModel.state.value)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantAppTheme {
        RestaurantsScreen(
            state = RestaurantScreenState(listOf(), true),
            onItemClick = {},
            onFavoriteClick = { _, _ -> },
            onTryAgainClick = {}
        )
    }
}