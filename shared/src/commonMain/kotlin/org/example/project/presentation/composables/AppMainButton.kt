package org.example.project.presentation.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.continue_btn
import downloaderkmpproductionapp.shared.generated.resources.ic_ad_vid_icon
import downloaderkmpproductionapp.shared.generated.resources.ic_arrowright_white
import downloaderkmpproductionapp.shared.generated.resources.ic_download_in_btn
import downloaderkmpproductionapp.shared.generated.resources.nunito_bold
import downloaderkmpproductionapp.shared.generated.resources.nunito_extrabold
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun AppMainButton(modifier: Modifier = Modifier, text: String=stringResource(Res.string.continue_btn),
                  showArrow: Boolean = false,
                  showDownload: Boolean  = false,
                  showAd: Boolean = false) {

    Box(modifier = modifier.clip(RoundedCornerShape(30.dp)).background(
        brush = Brush.horizontalGradient(
            colors = listOf(Color(0xFF2761FB),Color(0xFF2246F7),Color(0xFF0C3AED)),
        ), shape = RoundedCornerShape(30.dp)
    ).border(width = 2.dp,
        color = Color.White.copy(
            alpha = 0.47f
        ), shape =
            RoundedCornerShape(30.dp)
    ), contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.padding(
            vertical = 14.dp
        ), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            if (showDownload) {
                Image(
                    imageVector = vectorResource(
                        Res.drawable.ic_download_in_btn
                    ), contentDescription = null,
                    modifier = Modifier.padding(
                        end = 7.dp
                    )
                )
            }
            if (showAd) {
                Image(
                    imageVector = vectorResource(
                        Res.drawable.ic_ad_vid_icon
                    ), contentDescription = null,
                    modifier = Modifier.padding(
                        end = 8.dp
                    )
                )
            }
            AppText(
                text = text,
                font = Res.font.nunito_bold,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            if (showArrow) {
                Image(
                    imageVector = vectorResource(
                        Res.drawable.ic_arrowright_white
                    ), contentDescription = null,
                    modifier = Modifier.padding(
                        start = 10.dp
                    )
                )
            }
        }
        if (showAd){
            Box(modifier = Modifier.background(
                color = Color.White.copy(
                    alpha = 0.20f
                ), shape = RoundedCornerShape(topEnd = 12.dp,
                    bottomStart = 10.dp)
            ).padding(vertical = 6.dp).padding(
                start = 7.dp, end = 12.dp
            ).align(Alignment.TopEnd)) {
                AppText(text = "AD",
                    color = Color.White,
                    font = Res.font.nunito_extrabold,
                    fontSize = 12.sp)
            }
        }


    }
}

@Preview
@Composable
private fun AppMainButtonPrev() {
    AppMainButton(showAd = true)
}