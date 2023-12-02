package in2horizon.insite.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.TransViewModel

@Composable
fun PreferencesView() {

    val viewModel: TransViewModel = hiltViewModel()
    Column() {
        Text(text = "prefs")
        EngineChooser()
        Button(onClick = {
            viewModel.showPreferences(false)
        },){

           Text(text="Close")
        }
    }
}
