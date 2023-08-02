package com.example.restaurantapptheme.restaurants.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantDetails
import com.example.restaurantapptheme.restaurants.presentation.list.RestaurantIcon

@Composable
fun RestaurantDetailsScreen() {
    val viewModel: RestaurantDetailsViewModel = viewModel()
    val item = viewModel.state.value

    if (item != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            RestaurantIcon(
                icon = Icons.Filled.Place,
                modifier = Modifier.padding(
                    top = 32.dp,
                    bottom = 32.dp
                )
            )
            RestaurantDetails(
                title = item.title,
                description = item.description,
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            Text("More info comming soon")
        }
    }
}