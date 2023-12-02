package in2horizon.insite.preferences

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import in2horizon.insite.R
import in2horizon.insite.TransViewModel

@Composable
fun EngineChooser() {

    val viewModel: TransViewModel = hiltViewModel()
    val context = LocalContext.current

    val engineOptions = listOf(
        context.resources.getResourceEntryName(R.string.Google),
        context.resources.getResourceEntryName(R.string.Bing),
        context.resources.getResourceEntryName(R.string.DuckDuckGo),
    )
  //  val (selectedEngine,onEngineSelected) = remember { mutableStateOf(engineOptions[0]) }
    val PACKAGE_NAME=context.packageName
    val selectedEngine=viewModel.engineUrl.collectAsState()
    Column {
        engineOptions.forEach { name ->
            Row(Modifier
                .fillMaxWidth(1f)
                .padding(4.dp)
                .selectable(selected = (getResource4Name(context,name).equals(selectedEngine
                    .value)),
                    onClick = {
                        viewModel.setEngine(getResource4Name(context,name))

                    /*onEngineSelected
                    (name)*/ }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (getResource4Name(context,name).equals(selectedEngine
                        .value)),
                    onClick = { viewModel.setEngine(getResource4Name(context,name))    /*onEngineSelected(name) */})
                Text(
                    text = name, style = MaterialTheme.typography.bodyMedium.merge(),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
fun getId4Name(context: Context, name:String):Int{
    return context.resources.getIdentifier(name,"string",context.packageName)
}
fun getResource4Name(context:Context,name:String):String{

  return context.let{it.resources.getString(getId4Name(it,name))}
}
