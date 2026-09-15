package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.SocialApps
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_fb_social
import downloaderkmpproductionapp.shared.generated.resources.nunito_medium
import org.jetbrains.compose.resources.painterResource

@Composable
fun SocialAppItem(socialApps: SocialApps,onItemClick:(SocialApps)-> Unit) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(
            color = Color(0xFF003BEE).copy(
                alpha = 0.05f
            ), shape = RoundedCornerShape(18.dp)
        ).clickable(indication = null,
            interactionSource = remember
            { MutableInteractionSource() }){
            onItemClick(socialApps)
        }.padding(
            horizontal = 18.dp,
            vertical = 15.dp
        )) {
        Image(painter = painterResource(socialApps.image) , contentDescription = null,
            modifier = Modifier.size(40.dp))

        AppText(text = socialApps.name, modifier = Modifier.padding(top = 12.dp), fontSize = 14.sp,
            font = Res.font.nunito_medium,
            color = Color(0xFF121212))
    }



}

@Preview
@Composable
private fun SocialAppItemPrev() {
    SocialAppItem(SocialApps(image = Res.drawable.ic_fb_social,"FB"), onItemClick = {

    })
}