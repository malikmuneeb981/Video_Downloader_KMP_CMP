package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.WsStatusModel
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_delete
import downloaderkmpproductionapp.shared.generated.resources.ic_download
import downloaderkmpproductionapp.shared.generated.resources.ic_share
import downloaderkmpproductionapp.shared.generated.resources.ic_video_indicator
import org.jetbrains.compose.resources.vectorResource

@Composable
fun StatusSaverItem(statusModel: WsStatusModel, modifier: Modifier = Modifier,
                    saved: Boolean,
                    onSaveClick:(WsStatusModel)-> Unit,
                    onShareClick:(WsStatusModel) -> Unit,
                    onDeleteClick:(WsStatusModel)-> Unit,
                    onItemClick:(WsStatusModel)-> Unit) {

    val checkVideos: Boolean = statusModel.fileUri.toString().endsWith(".mp4")
    Box(modifier = modifier.fillMaxWidth().height(250.dp).clip(
        shape = RoundedCornerShape(12.dp),
    ).clickable{
        onItemClick(statusModel)
    },
        contentAlignment = Alignment.BottomCenter) {

        if (checkVideos){
            MediaThumbnailImage(
                model = statusModel.fileUri?:"",
            )
        }else{
            AsyncImage(model = statusModel.fileUri,
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentScale = ContentScale.Crop)
        }

        if (checkVideos) {
            Image(
                imageVector = vectorResource(
                    Res.drawable.ic_video_indicator
                ), contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = Color.White
                ), modifier = Modifier.size(
                    40.dp
                ).align(Alignment.Center)
            )
        }

        Row(modifier= Modifier.fillMaxWidth().background(
            color = Color(0xFF003BEE).copy(
                alpha = 0.5f
            ), shape = RoundedCornerShape(bottomStart = 12.dp,
                bottomEnd = 12.dp)
        ).padding(
            vertical = 10.dp
        ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            Column(modifier= Modifier.weight(1f).padding(
                top = 5.dp
            ).clickable{
                onShareClick(statusModel)
            }, horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Image(imageVector = vectorResource(
                    Res.drawable.ic_share
                ), contentDescription = null)
            }
            Column(modifier= Modifier.weight(1f).padding(
                top = 5.dp
            ).clickable{
               if (saved) onDeleteClick (statusModel) else onSaveClick(statusModel)
            }, horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Image(imageVector = vectorResource(
                    if (saved) Res.drawable.ic_delete else Res.drawable.ic_download
                ), contentDescription = null)
            }

        }
    }

}

@Preview
@Composable
private fun StatusSaverItemPrev() {
//    DownloaderMediaPlayerAsadTheme() {
        StatusSaverItem(statusModel = WsStatusModel(),
            saved = true, onSaveClick = {

            }, onShareClick = {

            }, onDeleteClick = {

            }, onItemClick = {

            })
//    }
}