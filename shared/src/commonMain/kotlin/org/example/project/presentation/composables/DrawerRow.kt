package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_moretools_mp3convert
import downloaderkmpproductionapp.shared.generated.resources.ic_right_arrow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DrawerRow(image: DrawableResource, text:String, onclick:()->Unit) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onclick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        Image(painter = painterResource( image),
            contentDescription = null, modifier = Modifier)

        AppText(text = text,
            textAlign = TextAlign.Left, modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp), textLines = 1,
            autoResize = true,
            fontSize = 16.sp)

        Image(painter = painterResource(Res.drawable.ic_right_arrow),
            contentDescription = null, colorFilter = ColorFilter.tint(color = Color(0xFF003BEE)))





    }

}

@Preview
@Composable
private fun DrawerRowPrev() {
    DrawerRow(image = Res.drawable.ic_moretools_mp3convert,
        text = "sdsdfsf", onclick ={

        })
}