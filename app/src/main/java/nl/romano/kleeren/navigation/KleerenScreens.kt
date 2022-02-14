package nl.romano.kleeren.navigation

import java.lang.IllegalArgumentException

sealed class KleerenScreens(val route: String) {
    object SplashScreen : KleerenScreens(route = "splash")
    object LoginScreen : KleerenScreens(route = "login")
    object CreateAccountScreen : KleerenScreens(route = "create_account")
    object HomeScreen : KleerenScreens(route = "home")
    object SearchScreen : KleerenScreens(route = "search")
    object ProductScreen : KleerenScreens(route = "product")
    object ShoppingCartScreen : KleerenScreens(route = "shopping_cart")
    object FavouriteScreen : KleerenScreens(route = "favourites")
    object AccountScreen : KleerenScreens(route = "account")
}
