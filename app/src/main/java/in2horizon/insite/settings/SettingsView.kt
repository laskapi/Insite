package in2horizon.insite.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.mainUi.Screen
import in2horizon.insite.mainUi.TransViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SettingsView() {

    val viewModel: TransViewModel = hiltViewModel()
    val ctx = LocalContext.current
    val colors = MaterialTheme.colorScheme
    val coroutineScope = rememberCoroutineScope()
    var showDeleteDictionaryDialog by remember {
        mutableStateOf(false)
    }
    var hideKeyboard by remember { mutableStateOf(false) }


    BackHandler(enabled = (viewModel.pager.currentPage== Screen.SETTINGS.ordinal)) {
        back2main(coroutineScope, viewModel)
    }

    Scaffold(
        topBar = {
            Column() {
                Text(
                    text = ctx.getString(R.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth
                            (1f)
                        .padding(8.dp, 8.dp, 8.dp, 8.dp)
                )
                Divider()
            }
        },
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                SettingsButton(
                    modifier = Modifier
                        .padding(10.dp),
                    onClick = {
                        back2main(coroutineScope, viewModel)
                    },
                ) {
                    Text(text = ctx.getString(R.string.close))
                }
            }
        }

    ) {


        Column(
            Modifier
                .background(color = colors.background)
                .verticalScroll(rememberScrollState())
                .padding(it)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { hideKeyboard = true }


        ) {


            SettingsItem(
                title = ctx.getString(R.string.selectEngine)
            ) {
                EngineChooserItem()
            }

            //------------------

            SettingsItem(
                title = ctx.getString(R.string.useStartPage),
                switchValue = viewModel.useStartPage,
                setSwitchValue = { switchValue -> viewModel.useStartPage = switchValue }

            ) {
                StartPageChooserItem(enabled = it, hideKeyboard = hideKeyboard) {
                    hideKeyboard = false
                }
            }
            //------------------
            SettingsItem(title = ctx.getString(R.string.deleteDictionary)) {
                SettingsButton(
                    onClick = { showDeleteDictionaryDialog = !showDeleteDictionaryDialog }) {
                    Text(text = ctx.getString(R.string.delete))
                }
            }

            //------------------


            if (showDeleteDictionaryDialog) {
                DeleteDictionaryDialog { showDeleteDictionaryDialog = false }
            }

        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
fun back2main(scope: CoroutineScope, viewModel: TransViewModel) {
    scope.launch {
        viewModel.pager.scrollToPage(
            Screen.MAIN
                .ordinal
        )
    }


}
