package nl.romano.kleeren.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    // content()
    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color.Magenta,
            elevation = 5.dp
        ) {
            Text(text = "Movies")
        }
    }) {
        Column {
            Text(text = "This is the home screen")
        }
    }
}
