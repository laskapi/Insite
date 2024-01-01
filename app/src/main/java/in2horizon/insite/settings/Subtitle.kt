package in2horizon.insite.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Subtitle(text: String){
    Text(text=text,style=MaterialTheme.typography.titleMedium, textAlign = TextAlign.Start,
        color=MaterialTheme.colorScheme.onBackground)
}