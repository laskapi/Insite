package in2horizon.insite.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.TransViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun StartPageChooser(color: Color, enabled: Boolean) {

    val viewModel: TransViewModel = hiltViewModel()
    val currentUrl = viewModel.searchText.collectAsState()

    val colors= MaterialTheme.colorScheme
    var startPage = remember { mutableStateOf(TextFieldValue(viewModel.getStartPage())) }

    val focusManager = LocalFocusManager.current

    var selectionRange: TextRange? = null
    var searchFocused by remember {
        mutableStateOf(false)
    }



    Column(Modifier.fillMaxWidth()) {


        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(4.dp)
                .selectable(selected = (viewModel.useStartPage),
                    onClick = {
                        viewModel.useStartPage = true

                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        )

        {

            Column() {


                TextField(
                    colors= TextFieldDefaults.textFieldColors(containerColor = colors.background),
                    shape= RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                searchFocused = true
                                val text = startPage.value.text
                                selectionRange = TextRange(0, text.length)

                            } else {
                                searchFocused = false
                            }
                        },

                    enabled = enabled,
                    value = startPage.value,
                    onValueChange = {

                     //   val selection = selectionRange ?: it.selection
                        selectionRange = null
                        startPage.value = it.copy()
                    },

                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.setStartPage(startPage.value.text)
                        focusManager.clearFocus()
                    })
                )

                SettingsButton(
                    modifier = Modifier.align(End),
                    enabled = enabled,
                    text = LocalContext.current.getString(R.string.useCurrent),
                ) {
                    startPage.value = currentUrl.value.copy()
                    viewModel.setStartPage(startPage.value.text)
                }

            }
        }
    }
}