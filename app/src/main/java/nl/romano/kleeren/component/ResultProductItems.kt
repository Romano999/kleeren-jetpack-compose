package nl.romano.kleeren.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.ui.theme.Green

@Composable
fun ProductItems(
    products: List<MProduct>,
    rowModifier: Modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
    onItemClick: (MProduct) -> Unit,
    onShoppingCartClick: (MProduct) -> Unit,
    onFavouriteClick: (MProduct) -> Unit
) {
    LazyColumn {
        items(products) { product ->
            Row(
                modifier = rowModifier
                    .clickable { onItemClick.invoke(product) }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = product.productUrl,
                        builder = {
                            this.listener(
                                onError = { request, ex ->
                                    Log.d(
                                        "Image",
                                        "ProductCard: Image not found, ex: ${ex.message}"
                                    )
                                }
                            )
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = product.name,
                        fontSize = MaterialTheme.typography.caption.fontSize.times(1.2),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "€ ${product.price}",
                        fontSize = MaterialTheme.typography.caption.fontSize.times(1.2),
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { onShoppingCartClick(product) },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Shopping cart icon",
                            tint = Green,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    IconButton(
                        onClick = { onFavouriteClick(product) },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite icon",
                            tint = Green,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
