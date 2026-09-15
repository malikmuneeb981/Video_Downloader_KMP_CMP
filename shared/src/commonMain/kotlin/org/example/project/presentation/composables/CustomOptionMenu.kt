package org.example.project.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.OptionMenuItem
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText

@Composable
fun CustomPopupMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    items: List<OptionMenuItem>,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    onItemClick: (OptionMenuItem) -> Unit
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        offset = offset,
        shape = RoundedCornerShape(28.dp),
        containerColor = Color(0xFFF5F5F5),
        shadowElevation = 8.dp
    ) {

        items.forEachIndexed { index, item ->

            DropdownMenuItem(
                text = {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(18.dp))

                        AppText(
                            text = item.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                },

                onClick = {
                    onItemClick(item)
                    onDismiss()
                },

                contentPadding = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 18.dp
                )
            )

            if (index != items.lastIndex) {

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}