package in2horizon.insite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip


@Composable
fun DeleteButton(modifier: Modifier = Modifier,colors:IconButtonColors=IconButtonDefaults.iconButtonColors(),
                 onClick: () ->
Unit) {
    Box(modifier = modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
        IconButton(
            onClick = onClick, modifier = Modifier
                .height(IntrinsicSize.Max)
                .width(IntrinsicSize.Max)
                .clip(RoundedCornerShape(20))
            ,
            colors = colors,
        )
        
        {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "delete",
                )
        }
    }
}