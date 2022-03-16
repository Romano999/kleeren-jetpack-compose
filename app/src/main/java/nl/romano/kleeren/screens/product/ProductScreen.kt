package nl.romano.kleeren.screens.product

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.component.CircularLoadingIndicator
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.navigation.KleerenScreens
import nl.romano.kleeren.ui.theme.Green

@Composable
fun ProductScreen(
    navController: NavController,
    productId: String?,
    viewModel: ProductScreenViewModel = hiltViewModel()
) {
    val context: Context = LocalContext.current
    val product: MProduct? = viewModel.foundProduct.value

    val onSuccessAction: (MProduct) -> Unit = { clickedProduct ->
        Toast.makeText(context, "${clickedProduct.name} added successfully!", Toast.LENGTH_SHORT).show()
    }

    val onFailureAction: () -> Unit = {
        Toast.makeText(context, "Adding item failed, please try again later", Toast.LENGTH_SHORT).show()
    }

    // content()
    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        BackButton(
            onBackButtonClick = { navController.popBackStack() }
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.92f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (product != null) {
                ProductDetails(
                    product = product
                )
                ProductOptions(
                    onCartClick = {
                        if (viewModel.loggedIn) {
                            viewModel.addToFirebaseProductList(
                                "shoppingCart",
                                onSuccessAction,
                                onFailureAction
                            )
                            return@ProductOptions
                        }
                        navController.navigate(KleerenScreens.LoginScreen.route)
                    },
                    onFavoriteClick = {
                        if (viewModel.loggedIn) {
                            viewModel.addToFirebaseProductList(
                                "favorites",
                                onSuccessAction,
                                onFailureAction
                            )
                            return@ProductOptions
                        }
                        navController.navigate(KleerenScreens.LoginScreen.route)
                    }
                )
            } else {
                CircularLoadingIndicator(
                    modifier = Modifier
                        .size(160.dp)
                        .padding(top = 20.dp)
                )
            }
        }
    }
}

@Composable
fun BackButton(
    onBackButtonClick: () -> Unit
) {
    IconButton(
        onClick = { onBackButtonClick() },
        modifier = Modifier.size(50.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Green,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductDetails(
    product: MProduct
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.6f)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = product.name,
            fontSize = MaterialTheme.typography.caption.fontSize.times(3)
        )
        Image(
            painter = rememberImagePainter(
                data = product.productUrl.toString(),
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Product Image",
            modifier = Modifier.size(220.dp)
        )

        Text(
            text = "€${product.price}",
            color = Green,
            fontSize = MaterialTheme.typography.caption.fontSize.times(2.2),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    bottom = 5.dp,
                    start = 5.dp
                )
        )
        Text(
            text = product.description,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
fun ProductOptions(
    onCartClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        // horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        RoundButton(
            onClick = onCartClick,
            text = "Add to cart",
            backgroundColor = Green,
            modifier = Modifier.fillMaxWidth(0.50f),
            textFontSize = 12.sp
        )
        RoundButton(
            onClick = onFavoriteClick,
            text = "Add to favorites",
            backgroundColor = Green,
            modifier = Modifier.fillMaxWidth(),
            textFontSize = 12.sp
        )
    }
}
