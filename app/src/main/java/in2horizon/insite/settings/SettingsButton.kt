package in2horizon.insite.settings

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import in2horizon.insite.R

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier, enabled: Boolean = true, onClick: () ->
    Unit, content: @Composable (RowScope.() -> Unit)
) {
    val colors = MaterialTheme.colorScheme
    ElevatedButton(
        modifier = modifier.padding(vertical = 4.dp),
        enabled = enabled,
        onClick = onClick,
        content=content,
      /*  colors = ButtonDefaults.buttonColors(
            disabledContentColor = colors.onPrimaryContainer,
            contentColor = colors.onPrimaryContainer,
            containerColor = colors.primaryContainer,

            ),*/

        shape = RoundedCornerShape(10.dp)
    )
}