package nl.romano.kleeren.screens.shoppingcart

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.component.ProductItems
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.navigation.KleerenScreens
import nl.romano.kleeren.ui.theme.Green

@Composable
fun ShoppingCartScreen(
    navController: NavController,
    viewModel: ShoppingCartScreenViewModel = hiltViewModel()
) {
    val shoppingCartProducts = viewModel.shoppingCartProducts
    val loggedIn = viewModel.loggedIn

    val onSignInButtonClick: () -> Unit = {
        navController.navigate(KleerenScreens.LoginScreen.route)
    }

    val onItemClick: (MProduct) -> Unit = { product ->
        navController.navigate(route = KleerenScreens.ProductScreen.route + "/${product.id}")
    }

    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your shopping cart",
                fontSize = MaterialTheme.typography.caption.fontSize.times(3)
            )
            if (loggedIn) {
                Column {
                    ShoppingCartList(
                        products = shoppingCartProducts,
                        onOrderButtonClick = { viewModel.placeOrder() },
                        onItemClick = onItemClick
                    )
                }
            } else {
                NotLoggedInScreen(
                    onSignInButtonClick
                )
            }
        }
    }
}

@Composable
fun ShoppingCartList(
    products: List<MProduct>,
    onOrderButtonClick: () -> Unit,
    onItemClick: (MProduct) -> Unit
) {
    ProductItems(
        products,
        rowModifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        onItemClick
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        RoundButton(
            onClick = onOrderButtonClick,
            text = "Place your order",
            backgroundColor = Green,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 20.dp)
        )
    }
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
                text = "Log in to see your shopping cart.",
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
