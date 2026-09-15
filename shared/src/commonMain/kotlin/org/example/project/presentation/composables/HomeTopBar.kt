package com.cyberarsenals.video.downloader.save.videos.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_home_topbar_download
import downloaderkmpproductionapp.shared.generated.resources.nunito_extrabold
import downloaderkmpproductionapp.shared.generated.resources.social_media
import downloaderkmpproductionapp.shared.generated.resources.splash_img
import downloaderkmpproductionapp.shared.generated.resources.video_downloader_upper
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeTopBar(downloadBtnClick:()-> Unit={}) {

    Column() {
        Row(modifier = Modifier.fillMaxWidth().height(getStatusBarHeightPx())) { }
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(
            bottom = 11.dp
        ),
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(Res.drawable.splash_img),
                contentDescription = null,
                modifier = Modifier.height(42.dp))
            Column(modifier = Modifier.padding(start = 12.dp, end = 2.dp).weight(1f)) {
                AppText(text = stringResource(Res.string.social_media),
                    font = Res.font.nunito_extrabold,
                    fontSize = 16.sp,
                    color = Color(0xFF1F2937),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start, textLines = 1
                )
                AppText(text = stringResource(Res.string.video_downloader_upper),
                    font = Res.font.nunito_extrabold,
                    fontSize = 16.sp,
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF002480),
                                Color(0xFF012176),
                                Color(0xFF01185D),
                            )
                        )
                    ), textLines = 1,textAlign = TextAlign.Start,modifier = Modifier.fillMaxWidth())
            }

            Image(painter = painterResource(Res.drawable.ic_home_topbar_download),
                contentDescription = null,
                modifier = Modifier.clickable{
                    downloadBtnClick()
                })
        }
        Spacer(
            modifier = Modifier.fillMaxWidth().height(2.dp).background(
                color = Color(0xFFECECEC)
            )
        )
    }


}

@Preview
@Composable
private fun HomeTopBarPrev() {
    HomeTopBar()
}