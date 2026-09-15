package com.cyberarsenals.video.downloader.save.videos.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.apply
import downloaderkmpproductionapp.shared.generated.resources.ic_backarrow
import downloaderkmpproductionapp.shared.generated.resources.ic_download
import downloaderkmpproductionapp.shared.generated.resources.ic_hamburger
import downloaderkmpproductionapp.shared.generated.resources.ic_lang_tick
import downloaderkmpproductionapp.shared.generated.resources.ic_options
import downloaderkmpproductionapp.shared.generated.resources.ic_premiumcrown
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import org.example.project.domain.models.appmodels.DropDownItem
import org.example.project.presentation.composables.DropDownMenu
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun TopBar(
    text: String,
    onPremiumClick:() -> Unit={},
    onBackClick: () -> Unit = {},
    showPremium: Boolean = true,
    options: Boolean = false,
    showDownloads: Boolean = false,
    onDownloadsClick:()-> Unit={},
    showBack: Boolean = true,
    showApplyLangTop: Boolean = false,
    showApplyTickTop: Boolean = false,
    onApplyLangClick:()-> Unit={},
    onApplyTickClick:()-> Unit={},
    dropDownItems: List<DropDownItem> = emptyList(),
) {
    var expanded by remember { mutableStateOf(false) }
    Column(){
        Row(modifier = Modifier.fillMaxWidth().height(getStatusBarHeightPx())) { }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp).padding(
                    bottom = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🔙 Back or Hamburger

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



            // 🧾 Title
            AppText(
                text = text,
                font = Res.font.nunito_semibold,
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding( start = if (showBack) 20.dp else 0.dp), textLines = 1,
                autoResize = true
            )

            // ✅ Right side icons
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {


                if (options) {
                    Box {
                        Image(
                            painter = painterResource(Res.drawable.ic_options),
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { expanded = !expanded } // 👈 show dropdown
                        )

                        DropDownMenu(
                            expanded = expanded,
                            onItemClick = {
                                expanded = false
                                it.onClick()
                            }, onDismissRequest = {
                                expanded = false
                            },
                            dropDownItems = dropDownItems
                        )
                    }
                }
                if (showDownloads){
                    Image(imageVector = vectorResource(Res.drawable.ic_download),
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onDownloadsClick() })
                }
                if (showApplyLangTop){
                    Row(modifier = Modifier.clip(
                        shape = RoundedCornerShape(12.dp)
                    ).clickable(onClick = {
                        onApplyLangClick()
                    }).border(
                        width = 1.dp
                        , color = Color(0xFF003BEE),
                        shape = RoundedCornerShape(12.dp)
                    ).padding(
                        vertical = 8.dp,
                        horizontal = 14.dp
                    ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        Image(
                            imageVector = vectorResource(
                                Res.drawable.ic_lang_tick
                            ),contentDescription = null
                        )
                        AppText(text=stringResource(Res.string.apply),
                            font = Res.font.nunito_semibold,
                            fontSize = 14.sp,
                            color = Color(0xFF003BEE),
                            modifier = Modifier.padding(
                                start = 4.dp
                            ))
                    }
                }
                if (showApplyTickTop){

                        Image(
                            imageVector = vectorResource(
                                Res.drawable.ic_lang_tick
                            ),contentDescription = null,
                            modifier = Modifier.size(
                                30.dp
                            ).clickable(onClick = {
                                onApplyTickClick()
                            })
                        )

                }
                if(showPremium){
                    Image(painter = painterResource(Res.drawable.ic_premiumcrown),contentDescription = null, modifier = Modifier.size(
                        45.dp
                    ).clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }){
                        onPremiumClick()
                    })
                }
            }
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
private fun TopBarPrev() {
    TopBar("Home",
        options = true, dropDownItems = listOf(
            DropDownItem(icon = Res.drawable.ic_hamburger, text = "", onClick = {


            })
        ), onPremiumClick = {

        },showDownloads = true, showApplyTickTop = true, onDownloadsClick = {

        })
}


@Composable
fun getStatusBarHeightPx(): Dp {
    val heightDp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    return heightDp
}
@Composable
fun getNavBarHeightPx(): Dp {
    val heightDp = WindowInsets.navigationBars.asPaddingValues().calculateTopPadding()
    return heightDp
}