package in2horizon.insite.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.TransViewModel

@Composable

fun StartPageChooser(color: Color) {
    val viewModel: TransViewModel = hiltViewModel()

    val currentUrl = viewModel.searchText.collectAsState()
    Column {

        Subtitle(text = LocalContext.current.getString(R.string.setStartPage), color)
        SettingsButton(
            text = LocalContext.current.getString(R.string.useCurrentPage) +
                    currentUrl
        ) {
            viewModel.setStartPage()
        }
    }
}