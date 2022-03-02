package nl.romano.kleeren.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.navigation.KleerenScreens

@Composable
fun ProductScreen(
    navController: NavController,
    productId: String?,
    viewModel: ProductScreenViewModel = hiltViewModel()
) {
    val product: MProduct? = viewModel.foundProduct.value
    // content()
    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column() {
            if (product != null) {
                ProductDetails(
                    product = product,
                    onBackButtonClick = { navController.popBackStack() }
                )
                ProductOptions(
                    onCartClick = {
                        if (viewModel.loggedIn) {
                            viewModel.addToCart()
                            return@ProductOptions
                        }
                        navController.navigate(KleerenScreens.LoginScreen.route)
                    },
                    onFavoriteClick = {
                        if (viewModel.loggedIn) {
                            viewModel.addToFavorites()
                            return@ProductOptions
                        }
                        navController.navigate(KleerenScreens.LoginScreen.route)
                    }
                )
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ProductDetails(
    product: MProduct,
    onBackButtonClick: () -> Unit
) {
    Button(onClick = { onBackButtonClick() }) {
        Text(text = "Go back")
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = product.name,
            fontSize = MaterialTheme.typography.caption.fontSize.times(3)
        )
        Image(
            painter = rememberImagePainter(
                data = product.productUrl.toString(),
                builder = {
                    // transformations(CircleCropTransformation())
                }
            ),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Product Image",
            modifier = Modifier.size(220.dp)
        )

        Text(text = "€${product.price}")
        Text(text = product.description)
    }
}

@Composable
fun ProductOptions(
    onCartClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Button(onClick = onCartClick) {
            Text(text = "Add to cart")
        }
        Button(onClick = onFavoriteClick) {
            Text(text = "Add to favorites")
        }
    }
}
