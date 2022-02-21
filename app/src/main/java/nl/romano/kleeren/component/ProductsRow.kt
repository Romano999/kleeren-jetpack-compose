package nl.romano.kleeren.component

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.ui.theme.Green
import nl.romano.kleeren.util.WebshopProducts

@Preview
@Composable
fun ProductsRow(
    title: String = "Title",
    products: List<MProduct> = WebshopProducts.fillWebshopWithProducts()
) {
    Column() {
        Text(
            text = title,
            color = Green,
            fontSize = MaterialTheme.typography.caption.fontSize.times(2.2),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    bottom = 20.dp,
                    start = 5.dp
                )
        )
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                )
            }
        }
    }
}
