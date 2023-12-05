package in2horizon.insite.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import in2horizon.insite.MainScaffold
import in2horizon.insite.TransViewModel
import in2horizon.insite.gecko.SessionsManager
import in2horizon.insite.preferences.PreferencesView

@Composable
fun MainComposable() {

    val viewModel:TransViewModel=hiltViewModel()
    val showPreferences=viewModel.showPreferences.collectAsState()

    if (showPreferences.value) {
        PreferencesView()
    } else {
        MainScaffold()
    }
}