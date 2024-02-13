package in2horizon.insite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import in2horizon.insite.gecko.SessionObserver
import in2horizon.insite.mainUi.RootComposable
import in2horizon.insite.mainUi.TransViewModel
import in2horizon.insite.mainUi.theme.InsiteTheme


@AndroidEntryPoint
class MainActivity :
    ComponentActivity(), SessionObserver {

    private val TAG = javaClass.name
    val viewModel: TransViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setSessionsManagerObserver(this)

    //    MobileAds.initialize(this) {}

        viewModel.getActiveSession()

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d(TAG, result.data.toString())
            }
        }


        setContent {

            InsiteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    RootComposable()
                }
            }
        }
    }

    override fun update(action: SessionObserver.Action, bundle: Bundle) {
        when (action) {
            SessionObserver.Action.SELECT -> {
                val text = bundle.getString(SessionObserver.TEXT)
                val intent = Intent()
                intent.action = Intent.ACTION_TRANSLATE

                text?.let {
                    if (it.isNotBlank()) {
                        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, it)
                        //          if (intent.resolveActivity(getPackageManager()) != null) {
                        viewModel.setSourceText(it)
                        viewModel.setSelectionCenterCoords(bundle.getSizeF(SessionObserver.COORDS))
                        startActivity(intent)
                        //          }
                    }
                }
            }

            SessionObserver.Action.NAVIGATE -> {
                (bundle.getString(SessionObserver.TEXT))?.let {
                    Log.d(TAG, "address set to " + it)
                    viewModel.setSearchText(TextFieldValue(it))
                }
            }
        }
    }


    @Preview
    @Composable
    fun ColorsPreview() {
        Column {
            Box(Modifier.background(MaterialTheme.colorScheme.primary)) {
                Text("primary")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
                Text("primary Container")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.onPrimaryContainer)) {
                Text("on primary Container")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.secondary)) {
                Text("secondary")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.secondaryContainer)) {
                Text("secondary Container")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.onSecondaryContainer)) {
                Text("on secondary Container")
            }

            Box(Modifier.background(MaterialTheme.colorScheme.tertiary)) {
                Text("tertiary")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)) {
                Text("tertiary Container")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.onTertiaryContainer)) {
                Text("on tertiary Container")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
                Text("onPrimary")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.onSecondary)) {
                Text("onSecondary")
            }
            Box(Modifier.background(MaterialTheme.colorScheme.onTertiary)) {
                Text("onTertiary")
            }

        }
    }

}