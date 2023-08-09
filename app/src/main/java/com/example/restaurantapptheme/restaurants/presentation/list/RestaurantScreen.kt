package com.example.restaurantapptheme.restaurants.presentation.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantapptheme.restaurants.data.ERROR_MESSAGE
import com.example.restaurantapptheme.restaurants.domain.model.Restaurant
import com.example.restaurantapptheme.restaurants.presentation.Description
import com.example.restaurantapptheme.ui.theme.RestaurantAppTheme

@Composable
fun RestaurantsScreen(
    state: RestaurantScreenState,
    onItemClick: (id: Int) -> Unit = {},
    onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit,
    onTryAgainClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when(state) {
            is RestaurantScreenState.LoadingState -> LoadingStateScreen()
            is RestaurantScreenState.ErrorState -> ErrorStateScreen(
                errorMessage = ERROR_MESSAGE,
                onTryAgainClick = { onTryAgainClick() }
            )
            is RestaurantScreenState.IdleState -> IdleContent(onItemClick, onFavoriteClick, state)
        }
    }
}

@Composable
fun IdleContent(
    onItemClick: (id: Int) -> Unit = {},
    onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit,
    state: RestaurantScreenState.IdleState,
) {
    LazyColumn(
        modifier = Modifier.background(color = Color.Gray),
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 8.dp
        ),
    ) {
        items(state.restaurants) { restaurant ->
            RestaurantItem(
                item = restaurant,
                onFavoriteClick = { id, oldValue -> onFavoriteClick(id, oldValue) },
                onItemClick = { id -> onItemClick(id) }
            )
        }
    }
}

@Composable
fun LoadingStateScreen() {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.semantics {
                    this.contentDescription = Description.RESTAURANT_LOADING
                }
            )
            Text(
                text = "Loading...",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ErrorStateScreen(
    errorMessage: String,
    onTryAgainClick: () -> Unit
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning icon",
                modifier = Modifier
                    .padding(8.dp)
                    .size(60.dp)
            )
            Text(
                text = errorMessage,
                textAlign = TextAlign.Center
            )

            TextButton(
                onClick = { onTryAgainClick() },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text ="Try Again",
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun RestaurantItem(
    item: Restaurant,
    onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit,
    onItemClick: (id:Int) -> Unit
) {

    val icon = if(item.isFavorite)
        Icons.Rounded.Favorite
    else
        Icons.Rounded.FavoriteBorder

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            RestaurantIcon(
                Icons.Filled.Place,
                Modifier.weight(0.15f)
            )
            RestaurantDetails(
                item.title,
                item.description,
                Modifier.weight(0.7f)
            )
            RestaurantIcon(icon = icon, modifier = Modifier.weight(0.15f)) {
                onFavoriteClick(item.id, item.isFavorite)
            }
        }
    }
}

@Composable
fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = {}) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
    )
}

@Composable
fun RestaurantDetails(
    title: String,
    description: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantAppTheme{
        RestaurantsScreen(
            state = RestaurantScreenState.ErrorState,
            onItemClick = {},
            onFavoriteClick = { _, _ -> },
            onTryAgainClick = {}
        )
    }
}