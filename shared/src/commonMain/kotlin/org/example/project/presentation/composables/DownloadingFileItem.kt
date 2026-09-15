package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.downloading_video
import downloaderkmpproductionapp.shared.generated.resources.ic_cross
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DownloadingFileItem(progress:Int, id: Long, thumbnail: String, cancelclick:(Long)-> Unit) {


    Column(modifier = Modifier.background(color = Color.White).fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.padding(vertical = 5.dp,
                horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(model = thumbnail, contentDescription = null,
                    modifier = Modifier.size(74.dp).clip(
                        RoundedCornerShape(12.dp)
                    ))
                Column(modifier = Modifier.weight(1f).height(74.dp).padding(horizontal = 10.dp,
                    vertical = 5.dp),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    AppText(text = stringResource(Res.string.downloading_video),
                        fontSize = 16.sp, font = Res.font.nunito_semibold,
                    )
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        AnimatedProgressBar(progress = progress, modifier = Modifier.weight(1f).padding(
                            top = 5.dp
                        ))

                    }

                }
                Column(modifier = Modifier.height(72.dp).padding(top = 10.dp, end = 10.dp),
                    verticalArrangement = Arrangement.Bottom) {

                    Image(imageVector = vectorResource(Res.drawable.ic_cross),
                        contentDescription = null,modifier = Modifier.clickable{
                            cancelclick(id)
                        }, colorFilter = ColorFilter.tint(
                            color = Color(0xFF8B96A6)
                        ))
                }
            }

        }
        Spacer(modifier = Modifier.fillMaxWidth().padding(
            top = 12.dp
        ).height(2.dp).background(
            color = Color(0xFFECECEC)
        ))
    }

}

@Preview
@Composable
private fun DownloadingFileItemPrev() {

        DownloadingFileItem(progress = 50,34242424L,""){

        }


}