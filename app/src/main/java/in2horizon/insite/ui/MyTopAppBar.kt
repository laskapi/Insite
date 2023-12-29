package in2horizon.insite.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.SearchTextField
import in2horizon.insite.TransViewModel
import org.mozilla.geckoview.GeckoSession


@Composable
fun MyTopAppBar(session: GeckoSession) {
    val TAG = "MyTopAppBar"
    val viewModel: TransViewModel = hiltViewModel()
    val address = viewModel.searchText.collectAsState()
    val focusManager = LocalFocusManager.current
    var selectionRange: TextRange? = null

    var searchFocused by remember {
        mutableStateOf(false)
    }


    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth(1f)
            .padding(4.dp)
            .onSizeChanged {

                Log.d(TAG, "" + it.width.dp + " :: " + it.height.dp)

            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = {

                session.goBack()

            }, modifier = Modifier.wrapContentSize(
                unbounded = true
            )
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }


        SearchTextField(
            value = address.value,
            onValueChange = {

                val selection = selectionRange ?: it.selection
                selectionRange = null
              viewModel.setAddress(
                    it.copy(
                        annotatedString = it.annotatedString,
                        selection = selection,
                        composition = it.composition
                    )
                )
            },

            modifier = Modifier
                .background(
                    Color.White, RoundedCornerShape(percent = 10)
                )
                .padding(4.dp)
                .weight(1f, true)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        searchFocused = true
                        val text = address.value.text
                        selectionRange = TextRange(0, text.length)


                    } else {
                        searchFocused = false
                    }
                }, keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.setUrl()
                focusManager.clearFocus()
            }),
            singleLine = true,
            placeholderText = "Search"
        )


        IconButton(
            onClick = {
                session.goForward()
            }, modifier = Modifier.wrapContentSize(
                unbounded = true
            )
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Forward")
        }

        if (!searchFocused) {

            IconButton(
                onClick = {
                    viewModel.showPreferences(true)
                }, modifier = Modifier.wrapContentSize(
                    unbounded = true
                )
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }


    }

}
