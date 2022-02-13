package nl.romano.kleeren.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column {
        Text(text = "This is the home screen")
    }
}
