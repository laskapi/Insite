package in2horizon.insite


import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.ui.DeleteButton
import in2horizon.insite.ui.LastTranslationsView
import in2horizon.insite.ui.MyGeckoView
import in2horizon.insite.ui.MyTopAppBar


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScaffold() {
    val TAG = "MainScaffold"
    val viewModel: TransViewModel = hiltViewModel()
    val translationToShow = viewModel.translationToShow.collectAsState()
    val url=viewModel.url.collectAsState()


    Scaffold(topBar = {
        MyTopAppBar(viewModel.getActiveSession())

    },
        content = {


                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(it), // Occupy the max size in the
                    // Compose UI tree
                    factory = { context ->
                        MyGeckoView(context, viewModel).apply {

                            setSession(viewModel.getActiveSession())
                            viewModel.setUrl()
                        //                   session?.loadUri(url.value)

                        }
                    },
                    update = { view ->
                        view.session?.loadUri(url.value)
                        Log.d(TAG, "updated to " +url.value)
                    }
                )
        },
        bottomBar = {
            val context = LocalContext.current
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
                    LastTranslationsView(
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),
                        textColor = MaterialTheme.colorScheme.onSurface,
                        translation = translationToShow.value,
                        onclick = {
                            val intent = Intent(Intent.ACTION_TRANSLATE)
                            intent.putExtra(Intent.EXTRA_PROCESS_TEXT, "")
                            context.startActivity(intent)
                        }
                    )

                    DeleteButton(colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme
                            .colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                        onClick = {
                            viewModel
                                .deleteTranslation(translationToShow.value)
                        })


                }
            }
        })
}
