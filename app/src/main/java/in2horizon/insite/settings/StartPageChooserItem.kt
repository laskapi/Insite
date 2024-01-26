package in2horizon.insite.settings

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.mainUi.TransViewModel
import in2horizon.insite.mainUi.Keyboard
import in2horizon.insite.mainUi.KeyboardAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartPageChooserItem(
    enabled: Boolean,
    hideKeyboard: Boolean = false,
    resetHideKeyboard: () -> Unit
) {

    val TAG = "StartPageChooserItem"
    val viewModel: TransViewModel = hiltViewModel()
    val currentUrl = viewModel.searchText.collectAsState()

    val colors = MaterialTheme.colorScheme
    var startPage = remember { mutableStateOf(TextFieldValue(viewModel.getStartPage())) }

    val focusManager = LocalFocusManager.current

    val isKeyboardOpen by KeyboardAsState()
    LaunchedEffect(isKeyboardOpen) {
        if (isKeyboardOpen== Keyboard.Closed) {
            focusManager.clearFocus()
        }
    }



    Column(Modifier.fillMaxWidth()) {


            Column(Modifier.fillMaxWidth()) {

                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = colors.surface,
                        textColor = colors.onPrimaryContainer,
                        unfocusedIndicatorColor = colors.surface,
                        focusedIndicatorColor = colors.surface,
                        disabledIndicatorColor = colors.surface,
                    ),

                    shape = RoundedCornerShape(10.dp),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .border(0.dp, colors.primary, RoundedCornerShape(10.dp))
                        .align(alignment = CenterHorizontally)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                Log.d(TAG, "is Focused")
                                val text = startPage.value.text

                            }
                        },
                    singleLine = true,
                    enabled = enabled,
                    value = startPage.value,
                    onValueChange = {

                        startPage.value = it.copy()
                    },

                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.setStartPage(startPage.value.text)
                        focusManager.clearFocus()
                    })
                )

                if (hideKeyboard) {
                    focusManager.clearFocus()
                    resetHideKeyboard()
                }

                SettingsButton(
                    enabled = enabled,
                    onClick = {
                        startPage.value = currentUrl.value.copy()
                        viewModel.setStartPage(startPage.value.text)
                    }
                ) {
                    Text(text = LocalContext.current.getString(R.string.useCurrent))
                }
     }

    }
}