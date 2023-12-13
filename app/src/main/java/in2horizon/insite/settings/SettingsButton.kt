package in2horizon.insite.settings

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import in2horizon.insite.R

@Composable
fun SettingsButton(text: String, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = colors.onPrimaryContainer,
            containerColor = colors.primaryContainer,
        )
    ) {
        Text(
            text = text
        )


    }

}