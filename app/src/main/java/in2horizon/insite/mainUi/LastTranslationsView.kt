package in2horizon.insite.mainUi

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.gmail.in2horizon.insite.db.Translation

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun LastTranslationsView(
    textColor:Color= MaterialTheme.colorScheme.onSurface,
    translation: Translation,
    modifier: Modifier = Modifier,
    onclick: () -> Unit

) {
    Box(
        modifier = modifier.clickable(enabled = true, onClick = onclick),
        contentAlignment = Alignment.Center
    ) {

        val LABEL = "LastTranslationsView"

        AnimatedContent(
            targetState = translation,
            label = LABEL,
            transitionSpec = {

                fadeIn(
                    animationSpec = TweenSpec(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                ) with

                        fadeOut()

            }
        ) { translation ->

            Column(
                modifier = modifier
            ) {

                Text(
                    modifier = Modifier
                        .basicMarquee(),
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium,
                    text = translation.src //+ " -"
                )

                Text(
                    modifier = Modifier
                        .basicMarquee(),
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    color=MaterialTheme.colorScheme.primary,

//                    color = textColor,
                    text = translation.dst
                )


            }
        }
    }
}