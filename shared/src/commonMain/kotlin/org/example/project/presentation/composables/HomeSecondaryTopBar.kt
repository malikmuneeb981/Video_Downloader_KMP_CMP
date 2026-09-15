package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.getStatusBarHeightPx
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_backarrow
import downloaderkmpproductionapp.shared.generated.resources.ic_premiumcrown
import downloaderkmpproductionapp.shared.generated.resources.nunito_extrabold
import org.example.project.navigation.NavRoutes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun HomeSecondaryTopBar(navController: NavController,text: String, showBack: Boolean = true,
                        onBackClick: () -> Unit = {},) {
    Column {
        Row(modifier = Modifier.fillMaxWidth().height(getStatusBarHeightPx())) { }
        Row(modifier = Modifier.fillMaxWidth().padding(
            horizontal = 16.dp,
        ).padding(
            bottom = 11.dp
        ),
            verticalAlignment = Alignment.CenterVertically) {
            if (showBack){
                Image(
                    imageVector = vectorResource(
                        Res.drawable.ic_backarrow
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(interactionSource = remember { MutableInteractionSource() },
                            indication = null) {
                            onBackClick()
                        }
                )
            }

            AppText(text = text,
                font = Res.font.nunito_extrabold,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f).padding( start = if (showBack) 20.dp else 0.dp), textLines = 1,
                textAlign = TextAlign.Start)
            Image(painter = painterResource(Res.drawable.ic_premiumcrown),
                contentDescription = null,
                modifier = Modifier.size(45.dp).clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        navController.navigate(NavRoutes.PremiumScreen.route)
                    }
                ))
        }
        Spacer(
            modifier = Modifier.fillMaxWidth().height(2.dp).background(
                color = Color(0xFFECECEC)
            )
        )
    }
}