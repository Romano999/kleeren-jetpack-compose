package nl.romano.kleeren.component

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import nl.romano.kleeren.ui.theme.Green

@Composable
fun CircularLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Green
) {
    CircularProgressIndicator(
        color = color,
        modifier = modifier
    )
}
