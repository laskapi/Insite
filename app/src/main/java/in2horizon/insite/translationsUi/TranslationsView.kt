package in2horizon.insite.translationsUi

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
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
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import in2horizon.insite.BuildConfig
import in2horizon.insite.R
import in2horizon.insite.mainUi.Screen
import in2horizon.insite.mainUi.TransViewModel
import in2horizon.insite.settings.SettingsButton
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun TranslationsView() {
    val viewModel: TransViewModel = hiltViewModel()
    val translationPagingItems = viewModel.allTranslationsPagedFlow.collectAsLazyPagingItems()
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomBarHeight = viewModel.bottomBarHeight.collectAsState()
    var ad: InterstitialAd? = null

    val backToMain = {
        coroutineScope.launch {
            Log.d("adShown", "")
            viewModel.pager.animateScrollToPage(Screen.MAIN.ordinal)
        }
    }


    BackHandler(enabled = (viewModel.pager.currentPage == Screen.TRANSLATIONS.ordinal)) {
        ad?.show(ctx as Activity)?:backToMain()
    }

    loadInterstitial(ctx, setAd = { ad = it }
    ) { backToMain() }

    Scaffold(topBar = {
    },
        content =
        {
            LazyColumn(Modifier.padding(it)) {
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
                        ad?.show(ctx as Activity)?:backToMain()
                    },
                ) {
                    Text(text = ctx.getString(R.string.close))
                }


            }

        })
}


fun loadInterstitial(ctx: Context, setAd: (InterstitialAd) -> Unit, onAdShown: () -> Unit) {

    InterstitialAd.load(
        ctx,
        BuildConfig.AD_UNIT_ID,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                setAd(interstitialAd)
                interstitialAd
                    .fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        onAdShown()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        onAdShown()
                    }


                }
            }

        }
    )
}

