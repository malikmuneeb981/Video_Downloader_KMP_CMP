package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_hamburger
import downloaderkmpproductionapp.shared.generated.resources.ic_options
import org.example.project.domain.models.appmodels.DropDownItem
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DropDownItemView(dropDownItem: DropDownItem, itemClick:()->Unit, lastItem: DropDownItem) {

    Column(modifier = Modifier.fillMaxWidth()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    itemClick()
                }
                .padding(vertical = 10.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = vectorResource(dropDownItem.icon),
                contentDescription = dropDownItem.text,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(18.dp)
            )
            AppText(
                text = dropDownItem.text,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 16.dp)
            )


        }
        if (dropDownItem.text != lastItem.text){
            Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(
                color = MaterialTheme.colorScheme.primary
            ))
        }

    }

}

@Preview
@Composable
private fun DropDownItemViewPrev() {
    DropDownItemView(dropDownItem =  DropDownItem(icon = Res.drawable.ic_options,
        text = "Favourite", onClick = {

        }), itemClick = {

    }, lastItem = DropDownItem(icon = Res.drawable.ic_hamburger,
        text = "Favourite", onClick = {

        }))
}