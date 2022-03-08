package nl.romano.kleeren.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.romano.kleeren.component.* // ktlint-disable no-wildcard-imports
import nl.romano.kleeren.model.UserSearch

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val searchTerm = rememberSaveable {
        mutableStateOf("")
    }

    val userSearches = viewModel.userSearchList.collectAsState().value
    val foundProducts = viewModel.foundProducts

    Scaffold(
        bottomBar = {
            BottomAppBar(
                elevation = 5.dp
            ) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
        Column {
            SearchInput(searchterm = searchTerm)
            if (foundProducts.isEmpty() || searchTerm.value == "") {
                UserSearchEntries(
                    userSearches,
                    onIconClick = { userSearch -> viewModel.delete(userSearch) },
                    onRowClick = { userSearch -> searchTerm.value = userSearch.searchTerm }
                )
            }

            Text(text = "Results")
            Button(onClick = {
                viewModel.addUserSearch(UserSearch(searchTerm = searchTerm.value))
                viewModel.searchProducts(UserSearch(searchTerm = searchTerm.value))
            }) {
                Text(text = "Search")
            }
            ProductItems(foundProducts)
        }
    }
}

@Composable
fun SearchInput(searchterm: MutableState<String>) {
    InputField(
        valueState = searchterm,
        labelText = "Search",
        imageVector = Icons.Default.Search,
    )
}
