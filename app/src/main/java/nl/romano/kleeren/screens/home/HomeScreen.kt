package nl.romano.kleeren.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.romano.kleeren.R
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.component.ProductsRow
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.ui.theme.Green

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val salesProducts = viewModel.salesProducts
    val arrivalProducts = viewModel.arrivalsProducts

    // content()
    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = true
                )
                .padding(bottom = 100.dp)
        ) {
            HomeScreenHeader()
            Spacer(modifier = Modifier.height(50.dp))
            SalesRow(salesProducts)
            Spacer(modifier = Modifier.height(50.dp))
            ArrivalsRow(arrivalProducts)
        }
    }
}

@Composable
fun SalesRow(products: List<MProduct>) {
    ProductsRow(
        title = "Sales",
        products = products
    )
}

@Composable
fun ArrivalsRow(products: List<MProduct>) {
    ProductsRow(
        "New Arrivals",
        products = products
    )
}

@Composable
fun HomeScreenHeader() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.homescreen_image),
            contentDescription = "Product Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            alpha = 0.5f
        )
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = "Start searching",
                color = Green,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.caption.fontSize.times(3),
            )

            RoundButton(
                onClick = { },
                text = "Search",
                backgroundColor = Green,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(top = 20.dp)
            )
        }
    }
}
