package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.Language
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_lang_tick
import downloaderkmpproductionapp.shared.generated.resources.ic_us
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LanguageSelectionItem(language: Language,onItemClick:(Language)-> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth().clip(
                shape = RoundedCornerShape(20.dp)
            ).border(
                width = if (language.isSelected) 2.dp else 0.dp,
                color = Color(0xFF003BEE).copy(
                    alpha = 0.47f
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                brush = Brush.verticalGradient(if (language.isSelected) listOf(
                    Color(0xFF2761FB).copy(
                        alpha = 0.35f
                    ),Color(0xFF2246F7).copy(
                        alpha = 0.35f
                    ),Color(0xFF0C3AED).copy(
                        alpha = 0.35f
                    ),
                ) else listOf(
                    Color(0xFFF9FAFB),Color(0xFFF9FAFB),Color(0xFFF9FAFB),
                )),
                shape = RoundedCornerShape(20.dp)
            ).clickable{
                onItemClick(language)
            }
            .padding(
                horizontal = 24.dp,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(
                language.flag
            ),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp).clip(
                    RoundedCornerShape(8.dp)
                )
        )

        AppText(
            text = language.name,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            color = Color(0xFF1F2937)
        )

        if (language.isSelected){
            Image(
                imageVector = vectorResource(
                    Res.drawable.ic_lang_tick
                ),
                contentDescription = null,
                modifier = Modifier.size(
                    20.dp
                )
            )
        }





    }


}

@Preview()
@Composable
private fun LanguageSelectionItemPrev() {
        LanguageSelectionItem(Language("English", Res.drawable.ic_us, "en", "Select this language",
            isSelected = false,
            shadowEnabled = true), onItemClick = {

        })

}