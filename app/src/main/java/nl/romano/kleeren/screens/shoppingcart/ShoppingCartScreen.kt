package nl.romano.kleeren.screens.shoppingcart

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.romano.kleeren.component.BottomNavigationBar

@Composable
fun ShoppingCartScreen(navController: NavController) {
    // content()
    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column {
            Text(text = "This is the shopping cart screen")
        }
    }
}
