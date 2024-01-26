package in2horizon.insite


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.gecko.MyNavigationDelegate
import in2horizon.insite.mainUi.MyBottomBar
import in2horizon.insite.mainUi.MyGeckoView
import in2horizon.insite.mainUi.MyTopAppBar
import in2horizon.insite.mainUi.Screen
import in2horizon.insite.mainUi.TransViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScaffold() {
    val TAG = "MainScaffold"
    val viewModel: TransViewModel = hiltViewModel()
    val url=viewModel.mUrl.collectAsState()
    val session=viewModel.getActiveSession()
    val canGoBack=(session.navigationDelegate as MyNavigationDelegate)
        .canGoBack.collectAsState()

    BackHandler(enabled = (viewModel.pager.currentPage== Screen.MAIN.ordinal) &&
            canGoBack.value) {
        session.goBack()
    }

    Scaffold( topBar = {
        MyTopAppBar(viewModel.getActiveSession())},
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
                        }
                    },
                    update = { view ->
                        view.session?.loadUri(url.value.url)
                        Log.d(TAG, "updated to " +url.value)
                    }
                )
        },
        bottomBar = {
            MyBottomBar()
        })
}
