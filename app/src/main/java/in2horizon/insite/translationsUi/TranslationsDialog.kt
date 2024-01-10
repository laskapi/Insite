package in2horizon.insite.translationsUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import in2horizon.insite.R
import in2horizon.insite.TransViewModel
import in2horizon.insite.settings.SettingsButton

@Composable
fun TranslationsDialog(dismiss: () -> Unit) {
    val viewModel: TransViewModel = hiltViewModel()
    val translationPagingItems = viewModel.allTranslationsPagedFlow.collectAsLazyPagingItems()
    val ctx= LocalContext.current

    Dialog(
        onDismissRequest = dismiss, properties = DialogProperties(
            dismissOnBackPress =
            true
        )
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.9f),
            shape = RoundedCornerShape(16.dp),

            ) {
/*        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
                .fillMaxWidth()
                .fillMaxHeight(.9f)
        ) {*/

            Column (modifier = Modifier.fillMaxSize()){
                LazyColumn(Modifier.weight(.8f)) {
                    items(count = translationPagingItems.itemCount) {
                        translationPagingItems.get(it)?.let { translation ->

                            TranslationsDialogItem(translation = translation)

                        }
                    }
                }
             //   Divider()
                SettingsButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(10.dp),
                    onClick = {
                       dismiss()
                    },
                ) {
                    Text(text = ctx.getString(R.string.close))
                }
            }
        }
    }
}