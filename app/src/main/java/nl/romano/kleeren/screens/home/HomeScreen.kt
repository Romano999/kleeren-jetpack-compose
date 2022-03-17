package nl.romano.kleeren.screens.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.ma.charts.legend.data.LegendPosition
import hu.ma.charts.pie.PieChart
import hu.ma.charts.pie.data.PieChartData
import hu.ma.charts.pie.data.PieChartEntry
import nl.romano.kleeren.R
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.component.CircularLoadingIndicator
import nl.romano.kleeren.component.ProductsRow
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.model.ReviewList
import nl.romano.kleeren.navigation.KleerenScreens
import nl.romano.kleeren.ui.theme.Green
import nl.romano.kleeren.util.JsonUtils

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val context: Context = LocalContext.current

    // JSON related
    val gson = Gson()
    val jsonString: String = JsonUtils.getJsonFromAssets(context, "review.json").toString()
    val reviewType = object : TypeToken<ReviewList>() {}.type
    val review = gson.fromJson<ReviewList>(jsonString, reviewType).reviews[0]

    // Chart related
    val SimpleColors = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
    )
    val pieChartData =
        PieChartData(
            entries = listOf(review.Would_recommend, review.Would_not_recommend, review.No_comment).reversed().mapIndexed { idx, value ->
                PieChartEntry(
                    value = value.toFloat(),
                    label = AnnotatedString(review.javaClass.declaredFields[idx].name.replace("_", " "))
                )
            },
            colors = SimpleColors,
            legendPosition = LegendPosition.Start,
            legendShape = CircleShape,
        )

    val onSearchButtonClick: () -> Unit = {
        navController.navigate(KleerenScreens.SearchScreen.route)
    }

    val onCardClick: (MProduct) -> Unit = { product ->
        navController.navigate(route = KleerenScreens.ProductScreen.route + "/${product.id}")
    }

    val salesProducts: MutableList<MProduct> = viewModel.salesProducts
    val arrivalProducts: MutableList<MProduct> = viewModel.arrivalsProducts

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
            HomeScreenHeader(onSearchButtonClick)
            Spacer(modifier = Modifier.height(50.dp))
            HomePieChart(pieChartData)
            Spacer(modifier = Modifier.height(50.dp))
            if (salesProducts.isEmpty() || arrivalProducts.isEmpty()) {
                CircularLoadingIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(160.dp)
                        .padding(top = 50.dp)
                )
            } else {
                SalesRow(salesProducts, onCardClick)
                Spacer(modifier = Modifier.height(50.dp))
                ArrivalsRow(arrivalProducts, onCardClick)
            }
        }
    }
}

@Composable
fun SalesRow(
    products: List<MProduct>,
    onSalesCardClick: (MProduct) -> Unit
) {
    ProductsRow(
        title = "Sales",
        products = products,
        onCardClick = onSalesCardClick
    )
}

@Composable
fun ArrivalsRow(
    products: List<MProduct>,
    onArrivalCardClick: (MProduct) -> Unit
) {
    ProductsRow(
        "New Arrivals",
        products = products,
        onCardClick = onArrivalCardClick
    )
}

@Composable
fun HomeScreenHeader(
    onSearchButtonClick: () -> Unit
) {
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
                onClick = onSearchButtonClick,
                text = "Search",
                backgroundColor = Green,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(top = 20.dp)
            )
        }
    }
}

@Composable
fun HomePieChart(pieChartData: PieChartData) {
    Text(
        text = "User recommendations",
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
        text = "We asked 100 people to tell us if they would recommend this app, here are the results!",
        fontSize = MaterialTheme.typography.caption.fontSize.times(1.2),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(
                bottom = 10.dp,
                start = 5.dp
            )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PieChart(
            data = pieChartData,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
