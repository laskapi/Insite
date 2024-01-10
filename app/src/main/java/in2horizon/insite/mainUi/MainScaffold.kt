package in2horizon.insite


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.mainUi.MyBottomBar
import in2horizon.insite.mainUi.MyGeckoView
import in2horizon.insite.mainUi.MyTopAppBar



@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScaffold() {
    val TAG = "MainScaffold"
    val viewModel: TransViewModel = hiltViewModel()
    val translationToShow = viewModel.translationToShow.collectAsState()
    val url=viewModel.url.collectAsState()
    var hideKeyboard by remember{ mutableStateOf(false) }


    Scaffold(modifier = Modifier.clickable { hideKeyboard=true }, topBar = {
        MyTopAppBar(viewModel.getActiveSession())/*,hideKeyboard=hideKeyboard) { hideKeyboard =
        false }*/

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

            MyBottomBar()

        })
}
