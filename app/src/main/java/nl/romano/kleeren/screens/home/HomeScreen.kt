package nl.romano.kleeren.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.navigation.KleerenScreens

@Composable
fun HomeScreen(navController: NavController) {
    // content()
    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column {
            Button(onClick = { navController.navigate(KleerenScreens.LoginScreen.route) }) {
                Text(text = "Among us")
            }
            Text(text = "This is the home screen")
        }
    }
}
