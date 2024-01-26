package in2horizon.insite.mainUi

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.MainScaffold
import in2horizon.insite.settings.SettingsView
import in2horizon.insite.translationsUi.TranslationsView

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun RootComposable() {

    val viewModel: TransViewModel = hiltViewModel()

    VerticalPager(pageCount = Screen.values().size, state = viewModel.pager, userScrollEnabled
    = false, beyondBoundsPageCount = 2) { page ->
        when (page) {

            Screen.MAIN.ordinal -> MainScaffold()
            Screen.SETTINGS.ordinal -> SettingsView()
            Screen.TRANSLATIONS.ordinal -> TranslationsView()


        }
    }
}
