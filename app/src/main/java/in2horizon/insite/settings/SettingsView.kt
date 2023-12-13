package in2horizon.insite.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.TransViewModel

@Composable
fun SettingsView() {

    val viewModel: TransViewModel = hiltViewModel()
    val ctx = LocalContext.current
    val F_COLOR = MaterialTheme.colorScheme.onPrimary
    val B_COLOR = MaterialTheme.colorScheme.primary

    Column(Modifier.background(color = B_COLOR)) {

        Text(
            text = ctx.getString(R.string.settings), style = MaterialTheme.typography.titleLarge,
            color = F_COLOR, textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth
                    (1f)
                .padding(0.dp, 0.dp, 0.dp, 4.dp)
        )

        MyDivider(color = F_COLOR)
        EngineChooser(color = F_COLOR)
        MyDivider(color = F_COLOR)
        StartPageChooser(color = F_COLOR)
        MyDivider(color = F_COLOR)

        Button(
            onClick = {
                viewModel.showPreferences(false)
            },
        ) {
            Text(text = ctx.getString(R.string.close))
        }
    }
}

@Composable
fun MyDivider(color: Color) {
    Divider(
        thickness = Dp.Hairline,
        color = color
    )

}