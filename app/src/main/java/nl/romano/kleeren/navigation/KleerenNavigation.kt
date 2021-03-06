package nl.romano.kleeren.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.romano.kleeren.screens.account.AccountScreen
import nl.romano.kleeren.screens.createaccount.CreateAccountScreen
import nl.romano.kleeren.screens.favourite.FavouriteScreen
import nl.romano.kleeren.screens.home.HomeScreen
import nl.romano.kleeren.screens.login.LoginScreen
import nl.romano.kleeren.screens.product.ProductScreen
import nl.romano.kleeren.screens.search.SearchScreen
import nl.romano.kleeren.screens.shoppingcart.ShoppingCartScreen
import nl.romano.kleeren.screens.splash.SplashScreen

@ExperimentalComposeUiApi
@Composable
fun KleerenNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = KleerenScreens.SplashScreen.route
    ) {
        composable(KleerenScreens.AccountScreen.route) {
            AccountScreen(navController = navController)
        }
        composable(KleerenScreens.CreateAccountScreen.route) {
            CreateAccountScreen(navController = navController)
        }
        composable(KleerenScreens.FavouriteScreen.route) {
            FavouriteScreen(navController = navController)
        }
        composable(KleerenScreens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(KleerenScreens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(
            KleerenScreens.ProductScreen.route + "/{product}",
            arguments = listOf(navArgument(name = "product") { type = NavType.StringType })
        ) {
            backStackEntry ->
            ProductScreen(
                navController = navController,
                backStackEntry.arguments?.getString("product")
            )
        }
        composable(KleerenScreens.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        composable(KleerenScreens.ShoppingCartScreen.route) {
            ShoppingCartScreen(navController = navController)
        }
        composable(KleerenScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
    }
}
