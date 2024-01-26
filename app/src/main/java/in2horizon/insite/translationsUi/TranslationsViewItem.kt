package in2horizon.insite.translationsUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.in2horizon.insite.db.Translation
import in2horizon.insite.R
import in2horizon.insite.mainUi.TransViewModel

@Composable
fun TranslationsViewItem(translation: Translation) {
    val viewModel: TransViewModel = hiltViewModel()

    ElevatedCard(
        /*colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
*/
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.padding(10.dp)
    ) {


        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    text = translation.src + " -", textAlign =
                    TextAlign.Start
                )
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.fillMaxWidth(0.2f))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        text = translation.dst, textAlign = TextAlign.Center
                    )
                }
            }
            IconButton(
                onClick = {
                    viewModel.deleteTranslation(translation)
                }, modifier = Modifier.wrapContentSize(
                )
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete)
                )
            }

        }
    }
}