package nl.romano.kleeren.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.ui.theme.DarkGreen
import nl.romano.kleeren.ui.theme.Green
import nl.romano.kleeren.util.WebshopProducts

@Composable
@Preview
fun ProductCard(product: MProduct = WebshopProducts.fillWebshopWithProducts()[0]) {
    Card(
        modifier = Modifier
            .height(210.dp)
            .width(150.dp)
    ) {
        Box(
            modifier = Modifier.size(100.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = product.productImageURL,
                    builder = {
                        this.listener(
                            onError = { request, ex ->
                                Log.d("Image", "ProductCard: Image not found, ex: ${ex.message}")
                            }
                        )
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp)
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = product.name,
                color = DarkGreen,
                fontSize = MaterialTheme.typography.caption.fontSize.times(1.5)
            )
            Text(
                text = "€ ${product.price}",
                color = Green,
                fontSize = MaterialTheme.typography.h6.fontSize
            )
        }
    }
}
