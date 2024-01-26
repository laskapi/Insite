package in2horizon.insite.mainUi

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyBottomBar() {


    val ctx = LocalContext.current
    val viewModel: TransViewModel = hiltViewModel()
    val translationToShow = viewModel.translationToShow.collectAsState()
    val show = remember { mutableStateOf(false) }
    val ad = remember { mutableStateOf(false) }

    val coroutineScope= rememberCoroutineScope()

    val localDensity= LocalDensity.current
    Column(modifier = Modifier.onGloballyPositioned { coords ->
        viewModel.setBottomBarHeight(with(localDensity) {coords.size
            .height.toDp()})
    }) {
        Divider()

        Row(
            modifier = Modifier
                .fillMaxWidth(1F)
                .height(IntrinsicSize.Min)
                .background(color = MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,

        ) {





            MyButton(modifier=Modifier.padding(start=10.dp),image = Icons.Outlined.Language,
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

                    coroutineScope.launch {
                        viewModel.pager.animateScrollToPage(Screen.TRANSLATIONS.ordinal)
                    }
                    //                   ad.value = true
                    //         show.value = true
                    /*
                        val intent = Intent(Intent.ACTION_TRANSLATE)
                        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, "")
                        ctx.startActivity(intent)
    */
                }
            )
            when {
                ad.value -> {
                    showIntertitial(ctx, show)
                    ad.value = false
                }

                show.value -> {

                    //    ad.value = false
                    //             TranslationsDialog(dismiss = { show.value = false
                    //      ad.value = true

                    //    })

//                    show.value = false
                }

            }

            MyButton(modifier=Modifier.padding(end=10.dp),image = Icons.Outlined.Delete,
                description = stringResource(id = R.string.delete),
                onClick = {
                    viewModel
                        .deleteTranslation(translationToShow.value)
                })


        }
    }


}

fun showIntertitial(ctx: Context, show: MutableState<Boolean>) {



}
