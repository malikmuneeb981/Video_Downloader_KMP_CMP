package com.cyberarsenals.video.downloader.save.videos.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun FeatureItem(
    title: String,
    icon: DrawableResource,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isLeftAligned: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isLeftAligned) Arrangement.Start else Arrangement.End
    ) {

        if (isLeftAligned) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(text = title)
        }

        if (!isLeftAligned) {
            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
        }
    }
}