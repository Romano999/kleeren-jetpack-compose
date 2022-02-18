package nl.romano.kleeren.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.* // ktlint-disable no-wildcard-imports

@Composable
fun RoundButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    contentPadding: PaddingValues = PaddingValues(10.dp),
    backgroundColor: Color = Color.White,
    text: String,
    textColor: Color = Color.White,
    textFontWeight: FontWeight = FontWeight.Bold,
    textFontSize: TextUnit = 18.sp,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        shape = shape,
        contentPadding = contentPadding,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        enabled = enabled

    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = textFontWeight,
            fontSize = textFontSize
        )
    }
}
