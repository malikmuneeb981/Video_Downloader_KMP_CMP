package org.example.project.presentation.btmSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.download
import downloaderkmpproductionapp.shared.generated.resources.hd_video
import downloaderkmpproductionapp.shared.generated.resources.ic_downloables_checkbox_checked
import downloaderkmpproductionapp.shared.generated.resources.ic_downloables_checkbox_unchecked
import downloaderkmpproductionapp.shared.generated.resources.ic_play_short_reels
import downloaderkmpproductionapp.shared.generated.resources.ic_vid_cam
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import downloaderkmpproductionapp.shared.generated.resources.ready_for_download
import org.example.project.domain.models.apiModels.DownloaderAPIResponse
import org.example.project.domain.models.apiModels.VideoDownloaderModel
import org.example.project.presentation.composables.AppMainButton
import org.example.project.presentation.composables.MediaThumbnailImage
import org.example.project.presentation.composables.VideoThumbnailView
import org.example.project.presentation.composables.WatchVidBtn
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadableBtmSheet(downloaderAPIResponse: DownloaderAPIResponse?,
                         dismiss:()->Unit,
                         downloadClick:(VideoDownloaderModel)->Unit,
                         watchClick:(VideoDownloaderModel)-> Unit) {

    val btmSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedVideo:VideoDownloaderModel ?=downloaderAPIResponse?.downloadables?.first()

    ModalBottomSheet(onDismissRequest = {
        dismiss()
    }, dragHandle = {

    }, shape = RoundedCornerShape(
        topStart = 14.dp,
        topEnd = 14.dp
    ), containerColor = Color.White, sheetState = btmSheetState) {

        Column(modifier = Modifier.background(Color.White).padding(top = 12.dp).padding(
            horizontal = 16.dp
        ),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(width = 56.dp,
                height = 4.dp).background(
                color = Color(0xFF8B96A6),
                shape = RoundedCornerShape(30.dp)
            )) { }

            Row(modifier = Modifier.fillMaxWidth().padding(
                top = 10.dp
            ).padding(horizontal = 10.dp,
                vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                Box(modifier = Modifier.
                    size(80.dp).background(
                        color = Color.Black.copy(
                            alpha = 0.1f
                        ), shape = RoundedCornerShape(8.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    MediaThumbnailImage(
                        model = downloaderAPIResponse?.image_url?:"",
                        contentDescription = null,
                        modifier = Modifier.matchParentSize()
                    )
                    Box(modifier = Modifier.background(
                        color = Color(0xFF1F2937).copy(
                            alpha = 0.25f
                        ),
                        shape = CircleShape
                    ).padding(
                        all = 11.dp
                    )){
                        Image(imageVector = vectorResource(
                            Res.drawable.ic_play_short_reels
                        ), contentDescription = null,
                            modifier = Modifier.size(15.dp))
                    }
                }
                Column(modifier = Modifier.weight(1f).padding(
                    start = 5.dp
                ),
                    verticalArrangement = Arrangement.Center,) {
                    AppText(text = "Video from ${downloaderAPIResponse?.platform}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        color = Color(0xFF1F2937),
                        font = Res.font.nunito_semibold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        )
                    AppText(text = stringResource(Res.string.ready_for_download),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        color = Color(0xFF8B96A6),
                        fontSize = 14.sp)
                }


            }

            Spacer(modifier = Modifier.fillMaxWidth().padding(
                top = 10.dp
            ).background(
                color = Color(0xFFECECEC)
            ).height(1.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(
                top = 25.dp
            )) {
                downloaderAPIResponse?.downloadables?.forEach { item ->
                   Row(modifier = Modifier.fillMaxWidth().padding(
                        start = 20.dp, end = 20.dp
                   ).clickable(indication = null,
                       interactionSource = remember { MutableInteractionSource() },
                       onClick = {
                           selectedVideo = item
                       })) {
                       Image(imageVector = vectorResource(
                           Res.drawable.ic_vid_cam
                       ), contentDescription = null)

                       AppText(text = stringResource(Res.string.hd_video),
                           font = Res.font.nunito_semibold,
                           fontSize = 14.sp,
                           color = Color(0xFF1F2937),
                           modifier = Modifier.weight(1f).padding(
                               start = 12.dp
                           ), textAlign = TextAlign.Start)
                       Image(imageVector = vectorResource(
                           if (item == selectedVideo){
                               Res.drawable.ic_downloables_checkbox_checked
                           }else{
                               Res.drawable.ic_downloables_checkbox_unchecked
                           }
                       ), contentDescription = null)

                   }
                }


            }
            Spacer(modifier = Modifier.fillMaxWidth().padding(
                top = 25.dp
            ).background(
                color = Color(0xFFECECEC)
            ).height(1.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(
                top = 16.dp , bottom = 25.dp
            ),
                horizontalArrangement = Arrangement.spacedBy(15.dp)) {

                WatchVidBtn(showEye = true, modifier = Modifier.weight(1f).clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }){
                    selectedVideo?.let {
                        watchClick(it)
                    }
                })
                AppMainButton(showDownload = true, modifier = Modifier.weight(1f).clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }){
                    selectedVideo?.let {
                        downloadClick(it)
                    }
                },
                    text = stringResource(Res.string.download))

            }
        }

    }

}

@Preview
@Composable
private fun DownloadablesBtmSheetPrev() {


        DownloadableBtmSheet(DownloaderAPIResponse(true,"dfsdf", listOf(VideoDownloaderModel())), dismiss = {

        }, downloadClick = { response,->

        }, watchClick = {

        })


}