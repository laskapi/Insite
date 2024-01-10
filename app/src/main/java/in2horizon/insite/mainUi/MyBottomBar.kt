package in2horizon.insite.mainUi

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.TransViewModel
import in2horizon.insite.translationsUi.TranslationsDialog

@Composable
fun MyBottomBar() {


    val ctx = LocalContext.current
    val viewModel: TransViewModel = hiltViewModel()
    val translationToShow = viewModel.translationToShow.collectAsState()
    val show = remember{mutableStateOf(false)}

    Column {
        Divider(
            thickness = Dp.Hairline,
            color =
            MaterialTheme
                .colorScheme.onPrimaryContainer

        )



        Row(
            modifier = Modifier
                .fillMaxWidth(1F)
                .height(IntrinsicSize.Min)
                .background(color = MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {


            MyButton(image = Icons.Outlined.Language,
                description = stringResource(id = R.string.open_translator),
                onClick = {
                    val intent = Intent(Intent.ACTION_TRANSLATE)
                    intent.putExtra(Intent.EXTRA_PROCESS_TEXT, "")
                    ctx.startActivity(intent)

                })


            LastTranslationsView(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                textColor = MaterialTheme.colorScheme.onSurface,
                translation = translationToShow.value,
                onclick = {
                    show.value=true
                    /*
                        val intent = Intent(Intent.ACTION_TRANSLATE)
                        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, "")
                        ctx.startActivity(intent)
    */
                }
            )
            when{ show.value->
                    TranslationsDialog(dismiss = { show.value = false })
            }

            MyButton(image = Icons.Outlined.Delete,
                description = stringResource(id = R.string.delete),
                onClick = {
                    viewModel
                        .deleteTranslation(translationToShow.value)
                })


        }
    }


}