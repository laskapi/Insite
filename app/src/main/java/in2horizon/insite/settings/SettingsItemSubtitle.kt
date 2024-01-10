package in2horizon.insite.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SettingsItemSubtitle(text: String){
    Text(text=text,style=MaterialTheme.typography.titleMedium, textAlign = TextAlign.Start,
        color=MaterialTheme.colorScheme.onBackground)
}