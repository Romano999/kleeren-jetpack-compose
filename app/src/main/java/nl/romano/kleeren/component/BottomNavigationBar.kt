package nl.romano.kleeren.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import nl.romano.kleeren.ui.theme.DarkGreen
import nl.romano.kleeren.ui.theme.Green
import nl.romano.kleeren.ui.theme.LightGreen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.ShoppingCart,
        BottomNavItem.Favourite,
        BottomNavItem.Account
    )
    BottomNavigation(
        backgroundColor = Green
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach {
            BottomNavigationItem(
                icon = { Icon(it.icon, contentDescription = it.title) },
                label = {
                    Text(
                        text = it.title,
                        maxLines = 1,
                        overflow = TextOverflow.Visible,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                selected = it.route == currentRoute,
                onClick = {
                    navController.navigate(it.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                alwaysShowLabel = false,
                selectedContentColor = LightGreen,
                unselectedContentColor = DarkGreen
            )
        }
    }
}
