package in2horizon.insite.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.TransViewModel

@Composable
fun DeleteDictionaryDialog(close: () -> Unit) {


    val viewModel: TransViewModel = hiltViewModel()
    val ctx = LocalContext.current

    AlertDialog(
        title = { Text(text = ctx.getString(R.string.deleteDictionary)) },
        text = { Text(text = ctx.getString(R.string.confirmDeleteDictionary)) },
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.deleteAllTranslations()
                    close()
                }
            ) {
                Text(ctx.getString(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    close()
                }
            ) {
                Text(ctx.getString(android.R.string.cancel))
            }
        }
    )
}