package nl.romano.kleeren.screens.search

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.romano.kleeren.component.* // ktlint-disable no-wildcard-imports
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.model.UserSearch
import nl.romano.kleeren.navigation.KleerenScreens
import nl.romano.kleeren.ui.theme.Green

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val context: Context = LocalContext.current
    val userSearches = viewModel.userSearchList.collectAsState().value
    val foundProducts = viewModel.foundProducts.collectAsState().value

    val searchTerm = rememberSaveable {
        mutableStateOf("")
    }
    val onSuccessAction: (MProduct) -> Unit = { clickedProduct ->
        Toast.makeText(
            context,
            "${clickedProduct.name} added successfully!", Toast.LENGTH_SHORT
        ).show()
    }

    val onFailureAction: () -> Unit = {
        Toast.makeText(
            context,
            "Adding item failed, please try again later", Toast.LENGTH_SHORT
        ).show()
    }

    val onItemClick: (MProduct) -> Unit = { product ->
        navController.navigate(route = KleerenScreens.ProductScreen.route + "/${product.id}")
    }

    val onShoppingCartClick: (MProduct) -> Unit = { product ->
        if (viewModel.loggedIn) {
            viewModel.addToFirebaseProductList(
                product.id,
                "shoppingCart",
                onSuccessAction,
                onFailureAction
            )
        } else {
            navController.navigate(KleerenScreens.LoginScreen.route)
        }
    }

    val onFavouriteClick: (MProduct) -> Unit = { product ->
        if (viewModel.loggedIn) {
            viewModel.addToFirebaseProductList(
                product.id,
                "favorites",
                onSuccessAction,
                onFailureAction
            )
        } else {
            navController.navigate(KleerenScreens.LoginScreen.route)
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                elevation = 5.dp
            ) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchInput(searchTerm = searchTerm)
            if (foundProducts.isEmpty() || searchTerm.value == "") {
                UserSearchEntries(
                    userSearches,
                    onIconClick = { userSearch -> viewModel.delete(userSearch) },
                    onRowClick = { userSearch -> searchTerm.value = userSearch.searchTerm }
                )
            }

            RoundButton(
                onClick = {
                    viewModel.addUserSearch(UserSearch(searchTerm = searchTerm.value))
                    viewModel.searchProducts(UserSearch(searchTerm = searchTerm.value))
                },
                text = "Search",
                backgroundColor = Green,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(top = 20.dp)
            )

            Divider()

            ProductItems(
                products = foundProducts,
                onItemClick = onItemClick,
                onShoppingCartClick = onShoppingCartClick,
                onFavouriteClick = onFavouriteClick
            )
        }
    }
}

@Composable
fun SearchInput(searchTerm: MutableState<String>) {
    InputField(
        valueState = searchTerm,
        labelText = "Search",
        imageVector = Icons.Default.Search,
    )
}
