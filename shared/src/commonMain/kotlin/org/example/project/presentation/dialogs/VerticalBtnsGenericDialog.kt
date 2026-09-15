package com.cyberarsenals.video.downloader.save.videos.presentation.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.GenericDialogData
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_premium_cam_crown
import org.example.project.presentation.composables.AppMainButton
import org.example.project.presentation.composables.BorderBtn
import org.jetbrains.compose.resources.vectorResource

@Composable
fun VerticalBtnsGenericDialog(dialogData: GenericDialogData,onDismiss:()-> Unit,
                                onPositiveBtnClick:()-> Unit,
                                onNegativeBtnClick:()-> Unit) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Column(modifier = Modifier.fillMaxWidth().background(color = Color.White,
            shape = RoundedCornerShape(24.dp)
        ).padding(
            vertical = 16.dp, horizontal = 20.dp
        ),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(imageVector = vectorResource(dialogData.image),
                contentDescription = null)

            AppText(text = dialogData.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(
                    top = 8.dp
                ))
            AppText(text = dialogData.subTitle,
                fontSize = 18.sp,
                modifier = Modifier.padding(
                    top = 4.dp
                ), color = Color(0xFF8B96A6))

            Column(modifier = Modifier.fillMaxWidth().padding(
                top = 24.dp
            ),
                verticalArrangement = Arrangement.spacedBy(15.dp)) {
                AppMainButton(text = dialogData.positiveBtnText, showAd = true, modifier = Modifier.fillMaxWidth().clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        onPositiveBtnClick()
                    }
                ))
                BorderBtn(text = dialogData.negativeBtnText, modifier = Modifier.fillMaxWidth().clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        onNegativeBtnClick()
                    }
                ))


            }


        }
    }
}

@Preview
@Composable
private fun HorizontalBtnsGenericDialogPrev() {
    VerticalBtnsGenericDialog(dialogData = GenericDialogData(image = Res.drawable.ic_premium_cam_crown,
        title = "sdfsf", subTitle = "sdssdsds", negativeBtnText = "dsdsfsdfs",
        positiveBtnText = "dnsdla"), onDismiss = {

    }, onNegativeBtnClick = {

    }, onPositiveBtnClick = {

    })
}