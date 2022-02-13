package nl.romano.kleeren.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.romano.kleeren.screens.account.AccountScreen
import nl.romano.kleeren.screens.createaccount.CreateAccountScreen
import nl.romano.kleeren.screens.favourite.FavouriteScreen
import nl.romano.kleeren.screens.home.HomeScreen
import nl.romano.kleeren.screens.login.LoginScreen
import nl.romano.kleeren.screens.product.ProductScreen
import nl.romano.kleeren.screens.search.SearchScreen
import nl.romano.kleeren.screens.shoppingcart.ShoppingCartScreen
import nl.romano.kleeren.screens.splash.SplashScreen

@Composable
fun KleerenNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = KleerenScreens.HomeScreen.name
    ) {
        composable(KleerenScreens.AccountScreen.name) {
            AccountScreen(navController = navController)
        }
        composable(KleerenScreens.CreateAccountScreen.name) {
            CreateAccountScreen(navController = navController)
        }
        composable(KleerenScreens.FavouriteScreen.name) {
            FavouriteScreen(navController = navController)
        }
        composable(KleerenScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(KleerenScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(KleerenScreens.ProductScreen.name) {
            ProductScreen(navController = navController)
        }
        composable(KleerenScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(KleerenScreens.ShoppingCartScreen.name) {
            ShoppingCartScreen(navController = navController)
        }
        composable(KleerenScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
    }
}
