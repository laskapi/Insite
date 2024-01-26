package in2horizon.insite.translationsUi

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import in2horizon.insite.R
import in2horizon.insite.mainUi.Screen
import in2horizon.insite.mainUi.TransViewModel
import in2horizon.insite.settings.SettingsButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun TranslationsView() {
    val viewModel: TransViewModel = hiltViewModel()
    val translationPagingItems = viewModel.allTranslationsPagedFlow.collectAsLazyPagingItems()
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomBarHeight = viewModel.bottomBarHeight.collectAsState()

    BackHandler(enabled = (viewModel.pager.currentPage== Screen.TRANSLATIONS.ordinal)) {
        back2main(coroutineScope, viewModel)
    }

    Scaffold(topBar = {},
        content =
        {
            LazyColumn(Modifier.padding(it)/*Modifier.weight(.8f)*/) {
                items(count = translationPagingItems.itemCount) {
                    translationPagingItems.get(it)?.let { translation ->
                        TranslationsViewItem(translation = translation)

                    }
                }
            }

        },
        bottomBar = {
            Box(modifier = Modifier.height(bottomBarHeight.value)) {
                Divider(Modifier.align(Alignment.TopCenter))

                SettingsButton(
                    modifier = Modifier
                        .align(Alignment.Center),
                    onClick = {
                        back2main(coroutineScope, viewModel)
                    },
                ) {
                    Text(text = ctx.getString(R.string.close))
                }


            }

        })
}

@OptIn(ExperimentalFoundationApi::class)
fun back2main(scope: CoroutineScope, viewModel: TransViewModel) {
    scope.launch {
        viewModel.pager.animateScrollToPage(
            Screen.MAIN
                .ordinal
        )
    }


}
