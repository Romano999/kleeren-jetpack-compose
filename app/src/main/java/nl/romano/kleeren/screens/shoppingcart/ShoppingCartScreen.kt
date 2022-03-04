package nl.romano.kleeren.screens.shoppingcart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.component.ProductItems
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.ui.theme.Green

@Composable
fun ShoppingCartScreen(
    navController: NavController,
    viewModel: ShoppingCartScreenViewModel = hiltViewModel()
) {
    val shoppingCartProducts = viewModel.shoppingCartProducts

    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column {
            ShoppingCartList(shoppingCartProducts)
        }
    }
}

@Composable
fun ShoppingCartList(products: List<MProduct>) {
    Text(
        text = "Your shopping cart",
        fontSize = MaterialTheme.typography.caption.fontSize.times(3)
    )
    ProductItems(
        products,
        rowModifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        RoundButton(
            onClick = { },
            text = "Place your order",
            backgroundColor = Green,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 20.dp)
        )
    }
}
