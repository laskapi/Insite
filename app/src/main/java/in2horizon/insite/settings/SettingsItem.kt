package in2horizon.insite.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.unit.dp

@Composable
fun SettingsItem(
    title: String,/* switchValue: KMutableProperty0<Boolean>? = null,*/
    switchValue:Boolean?=null,setSwitchValue: (Boolean)->Unit={},content:
    @Composable()
        (enabled: Boolean) ->
    Unit
) {

    val colors= MaterialTheme.colorScheme


    var switchState by remember { mutableStateOf(switchValue/*?.get()*/ ?: true) }

    Column(modifier = Modifier.fillMaxWidth()
        .padding(10.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            SettingsItemSubtitle(text = title)

            switchValue?.let {

                Switch(modifier=Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                    colors = SwitchDefaults.colors(

                        uncheckedTrackColor = colors.background,
                        uncheckedBorderColor = colors.onBackground,
                        checkedBorderColor = colors.onBackground,
                        checkedIconColor = colors.onPrimary,
                        checkedTrackColor = colors.background,
                        checkedThumbColor = colors.primary
                    ),
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
                       setSwitchValue(it)// switchValue.set(it)
                    },
                )
            }
        }

       Column(modifier = Modifier.fillMaxWidth(1f)
        //        horizontalAlignment = Alignment.Start
            ) {
                content(switchState)
            }
    }
}