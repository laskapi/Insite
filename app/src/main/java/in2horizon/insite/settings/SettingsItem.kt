package in2horizon.insite.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import kotlin.reflect.KMutableProperty0

@Composable
fun SettingsItem(
    title: String, switchValue: KMutableProperty0<Boolean>? = null, color: Color, content:
    @Composable()
        (enabled: Boolean) ->
    Unit
) {


    var switchState by remember { mutableStateOf(switchValue?.get() ?: true) }

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Subtitle(text = title, color = color)

            switchValue?.let {

                Switch(
                    colors = SwitchDefaults.colors(

                        uncheckedTrackColor = MaterialTheme.colorScheme.background,
                        uncheckedBorderColor = color),
                    checked = switchState,
                    thumbContent = if (switchState) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },

                    onCheckedChange = {
                        switchState = it
                        switchValue.set(it)
                    },
                )
            }
        }

        Row(
        )
        {
      //      Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                content(switchState)
            }
       //     Spacer(modifier = Modifier.weight(1f))
        }
    }
}