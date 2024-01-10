package in2horizon.insite.settings

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
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
    val colors = MaterialTheme.colorScheme

    var showDeleteDictionaryDialog by remember {
        mutableStateOf(false)
    }
    var hideKeyboard by remember { mutableStateOf(false) }


    Column(
        Modifier
            .background(color = colors.background)
            .verticalScroll(rememberScrollState())
            .clickable { hideKeyboard = true }
    ) {

        Text(
            text = ctx.getString(R.string.settings), style = MaterialTheme.typography.titleLarge,
            color = colors.onBackground, textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth
                    (1f)
                .padding(8.dp, 8.dp, 8.dp, 8.dp)
        )


        MyDivider()
        SettingsItem(
            title = ctx.getString(R.string.selectEngine)
        ) {
            EngineChooserItem()

            MyDivider()
            SettingsItem(
                title = ctx.getString(R.string.useStartPage),
                switchValue = viewModel.useStartPage,// viewModel::useStartPage
                setSwitchValue = { switchValue -> viewModel.useStartPage = switchValue }

            ) {
                StartPageChooserItem(enabled = it)/*, hideKeyboard = hideKeyboard) { hideKeyboard
                 = false }*/
            }



            MyDivider()
            SettingsItem(title = ctx.getString(R.string.deleteDictionary)) {
                SettingsButton(
                    //            modifier = Modifier.align(End),
                    onClick = { showDeleteDictionaryDialog = !showDeleteDictionaryDialog }) {
                    Text(text = ctx.getString(R.string.delete))
                }
            }
            if (showDeleteDictionaryDialog) {
                DeleteDictionaryDialog { showDeleteDictionaryDialog = false }
            }

            MyDivider()

            SettingsButton(
                modifier = Modifier
                    .align(End)
                    .padding(10.dp),
                onClick = {
                    viewModel.showPreferences(false)
                },
            ) {
                Text(text = ctx.getString(R.string.close))
            }
        }
    }
}

@Composable
fun MyDivider() {
    Divider(
        thickness = Dp.Hairline,
        color = MaterialTheme.colorScheme.onBackground
    )
}