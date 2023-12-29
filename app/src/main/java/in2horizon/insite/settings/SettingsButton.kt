package in2horizon.insite.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import in2horizon.insite.R

@Composable
fun SettingsButton(modifier:Modifier=Modifier,text: String,enabled:Boolean=true, onClick: () ->
Unit) {
    val colors = MaterialTheme.colorScheme
    Button(
        modifier=modifier.
        padding(4.dp),
        enabled=enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = colors.onPrimary,
            contentColor = colors.onPrimary,
            containerColor = colors.primary,

        ),


        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text
        )


    }

}