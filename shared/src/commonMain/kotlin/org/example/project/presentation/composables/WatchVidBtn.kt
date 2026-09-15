package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_watch_vid_eye
import downloaderkmpproductionapp.shared.generated.resources.nunito_bold
import downloaderkmpproductionapp.shared.generated.resources.watch_video
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource


@Composable
fun WatchVidBtn(modifier: Modifier = Modifier, text: String=stringResource(Res.string.watch_video),
                showEye: Boolean  = false) {

    Row(modifier = modifier.clip(RoundedCornerShape(30.dp)).border(width = 2.dp,
        color = Color(0xFF003BEE), shape =
            RoundedCornerShape(30.dp)
    ).padding(
        vertical = 14.dp
    ), horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        if (showEye){
            Image(imageVector = vectorResource(
                Res.drawable.ic_watch_vid_eye
            ), contentDescription = null,
                modifier = Modifier.padding(
                    end = 7.dp
                ))
        }
        AppText(text = text,
            font = Res.font.nunito_bold,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF003BEE))


    }
}

@Preview
@Composable
private fun WatchVidBtnPrev() {
    WatchVidBtn(showEye = true)
}