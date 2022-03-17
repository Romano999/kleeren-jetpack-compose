package nl.romano.kleeren.screens.favourite

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.romano.kleeren.component.* // ktlint-disable no-wildcard-imports
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.navigation.KleerenScreens
import nl.romano.kleeren.ui.theme.Green

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavouriteScreenViewModel = hiltViewModel()
) {
    val favouriteProducts = viewModel.favouriteProducts.toMutableList()
    val loggedIn = viewModel.loggedIn
    val deletingProduct = viewModel.deletingProduct.value

    val onSignInButtonClick: () -> Unit = {
        navController.navigate(KleerenScreens.LoginScreen.route)
    }

    val onItemClick: (MProduct) -> Unit = { product ->
        navController.navigate(route = KleerenScreens.ProductScreen.route + "/${product.id}")
    }

    val onDeleteClick: (MProduct) -> Unit = { product ->
        viewModel.deleteProduct(product)
        viewModel.getFavourites()
    }

    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your favorites",
                fontSize = MaterialTheme.typography.caption.fontSize.times(3)
            )
            if (loggedIn) {
                FavouriteList(
                    favouriteProducts,
                    onItemClick,
                    onDeleteClick
                )
            } else {
                NotLoggedInScreen(
                    onSignInButtonClick
                )
            }
        }
    }
}

@Composable
fun FavouriteList(
    products: MutableList<MProduct>,
    onItemClick: (MProduct) -> Unit,
    onDeleteClick: (MProduct) -> Unit
) {
    AccountProductItems(
        products,
        rowModifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        onItemClick,
        onDeleteClick
    )
}

@Composable
fun NotLoggedInScreen(onSignInButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You are currently not logged in.",
                fontSize = MaterialTheme.typography.caption.fontSize.times(1.5)
            )
            Text(
                text = "Log in to see your favourites list.",
                fontStyle = FontStyle.Italic
            )
            RoundButton(
                onClick = onSignInButtonClick,
                text = "Login in",
                backgroundColor = Green,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(top = 20.dp)
            )
        }
    }
}
