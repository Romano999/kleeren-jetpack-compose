package nl.romano.kleeren.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import nl.romano.kleeren.navigation.KleerenScreens

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("Home", Icons.Default.Home, KleerenScreens.HomeScreen.route)
    object Search : BottomNavItem("Search", Icons.Default.Search, KleerenScreens.SearchScreen.route)
    object ShoppingCart : BottomNavItem("Cart", Icons.Default.ShoppingCart, KleerenScreens.ShoppingCartScreen.route)
    object Favourite : BottomNavItem("Favourite", Icons.Default.FavoriteBorder, KleerenScreens.FavouriteScreen.route)
    object Account : BottomNavItem("Account", Icons.Default.Person, KleerenScreens.AccountScreen.route)
}
