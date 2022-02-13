package nl.romano.kleeren.navigation

import java.lang.IllegalArgumentException

enum class KleerenScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    SearchScreen,
    ProductScreen,
    ShoppingCartScreen,
    FavouriteScreen,
    AccountScreen;

    fun fromRoute(route: String?): KleerenScreens =
        when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            HomeScreen.name -> HomeScreen
            SearchScreen.name -> SearchScreen
            ProductScreen.name -> ProductScreen
            ShoppingCartScreen.name -> ShoppingCartScreen
            FavouriteScreen.name -> FavouriteScreen
            AccountScreen.name -> AccountScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised.")
        }
}
