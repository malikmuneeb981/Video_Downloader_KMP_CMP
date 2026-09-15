package org.example.project.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import org.example.project.domain.models.appmodels.DropDownItem

@Composable
fun DropDownMenu(expanded: Boolean,
                 onDismissRequest:()-> Unit,
                 dropDownItems: List<DropDownItem>,
                 onItemClick:(DropDownItem)-> Unit) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
        properties = PopupProperties(
            //usePlatformDefaultWidth = false,
            clippingEnabled = false
        ),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface.copy(
                alpha = 0.3f
            ), RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
    ) {
        dropDownItems.forEach { item ->
            DropDownItemView(dropDownItem = item, itemClick = {
                onItemClick(item)

            }, lastItem = dropDownItems[dropDownItems.size-1])
        }
    }

}