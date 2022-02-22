package nl.romano.kleeren.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import nl.romano.kleeren.model.MProduct

@Composable
fun ProductItems(products: List<MProduct>) {
    LazyColumn {
        items(products) { product ->
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .clickable {
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
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
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = product.name,
                    fontSize = MaterialTheme.typography.caption.fontSize.times(1.5),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
