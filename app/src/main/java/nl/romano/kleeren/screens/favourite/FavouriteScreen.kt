package nl.romano.kleeren.screens.favourite

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.component.ProductItems
import nl.romano.kleeren.model.MProduct

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavouriteScreenViewModel = hiltViewModel()
) {
    val favouriteProducts = viewModel.favouriteProducts
    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column {
            FavouriteList(favouriteProducts)
        }
    }
}

@Composable
fun FavouriteList(products: List<MProduct>) {
    Text(
        text = "Your favorites",
        fontSize = MaterialTheme.typography.caption.fontSize.times(3)
    )
    ProductItems(
        products,
        rowModifier = Modifier.padding(10.dp).fillMaxWidth()
    )
}
