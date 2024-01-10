package in2horizon.insite.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.TransViewModel

@Composable
fun EngineChooserItem() {

    val viewModel: TransViewModel = hiltViewModel()
    val ctx = LocalContext.current
    val colors=MaterialTheme.colorScheme

    val engineOptions = listOf(
        ctx.resources.getResourceEntryName(R.string.Google),
        ctx.resources.getResourceEntryName(R.string.Bing),
        ctx.resources.getResourceEntryName(R.string.DuckDuckGo),
    )

    val selectedEngine = viewModel.engineUrl.collectAsState()




    engineOptions.forEach { name ->
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(4.dp)
                .selectable(selected = (getResource4Name(ctx, name).equals(
                    selectedEngine
                        .value
                )),
                    onClick = {
                        viewModel.setEngine(getResource4Name(ctx, name))

                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(colors = RadioButtonDefaults.colors(
                selectedColor = colors.onBackground,
                unselectedColor = colors.onBackground
            ),
                selected = (getResource4Name(ctx, name).equals(
                    selectedEngine
                        .value
                )),
                onClick = { viewModel.setEngine(getResource4Name(ctx, name)) })
            Text(
                color = colors.onBackground,
                text = name, style = MaterialTheme.typography.bodyMedium.merge(),
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

private fun getId4Name(context: Context, name: String): Int {
    return context.resources.getIdentifier(name, "string", context.packageName)
}

private fun getResource4Name(context: Context, name: String): String {

    return context.let { it.resources.getString(getId4Name(it, name)) }
}
