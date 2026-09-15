package org.example.project.presentation.composables


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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.OptionMenuItem
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.delete
import downloaderkmpproductionapp.shared.generated.resources.ic_delete
import downloaderkmpproductionapp.shared.generated.resources.ic_more_fav_reels
import downloaderkmpproductionapp.shared.generated.resources.ic_share_black
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import downloaderkmpproductionapp.shared.generated.resources.share
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DownloadedFilesItem(
    file: String, optionsClick:(String, String)-> Unit) {
    var showMenu by remember {
        mutableStateOf(false)
    }
//    val thumbnail = remember(file) { getVideoThumbnail(file) }
//    val fileSize = remember(file) { getFileSize(file) }
    val menuItems = listOf(
        OptionMenuItem(
            id = 1,
            title = stringResource(Res.string.share),
            icon = vectorResource(Res.drawable.ic_share_black)
        ),
        OptionMenuItem(
            id = 2,
            title = stringResource(Res.string.delete),
            icon = vectorResource(Res.drawable.ic_delete,)
        )
    )
    Column(modifier = Modifier.fillMaxWidth())
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                optionsClick("Play",file)
            },
            horizontalArrangement = Arrangement.spacedBy(
                15.dp
            ),
            verticalAlignment = Alignment.CenterVertically)
        {
            Box(modifier = Modifier
                .size(
                    74.dp
                )
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )) {
                MediaThumbnailImage(
                    model = file,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

//                        Box(modifier = Modifier
//                            .padding(
//                                bottom = 8.dp, end = 8.dp
//                            )
//                            .background(
//                                color = Color.Black,
//                                shape = RoundedCornerShape(4.dp)
//                            )
//                            .padding(
//                                horizontal = 4.dp
//                            )) {
//                            AppText(text = it.formattedDuration,
//                                color = Color.White,
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.SemiBold)
//                        }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                AppText(text = file.substringAfterLast("/"), fontSize = 16.sp, font = Res.font.nunito_semibold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start)

                Row(modifier = Modifier.fillMaxWidth().padding(
                    top = 13.dp
                ), verticalAlignment = Alignment.CenterVertically) {
                    AppText(text =  "",
                        color = Color(0xFF8B96A6), fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start)
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    showMenu = true
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = vectorResource(
                            Res.drawable.ic_more_fav_reels,
                        ), contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFF8B96A6))
                        CustomPopupMenu(
                            expanded = showMenu,
                            onDismiss = {
                                showMenu = false
                            },
                            items = menuItems,

                            // Position close to the option menu box
                            offset = DpOffset(
                                x = 0.dp,
                                y = 0.dp
                            ),

                            onItemClick = { menuItem ->
                                when (menuItem.id) {
                                    1 -> {
                                        optionsClick("Share",file)
                                        // Share

                                    }
                                    2 -> {
                                        optionsClick("Delete",file)
                                    }
                                }
                            }
                        )
                    }
                }
//                        AppText(text = it.name,
//                            textLines = 1,
//                            fontWeight = FontWeight.Medium,
//                            modifier = Modifier.fillMaxWidth()
//                            , textAlign = TextAlign.Start,
//                            fontSize = 16.sp)
//
//                        AppText(text = "${it.formattedDate}\nSize: ${it.readableSize}",
//                            fontWeight = FontWeight.Light,
//                            fontSize = 12.sp,
//                            textAlign = TextAlign.Start,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant)
            }


        }

        Spacer(modifier = Modifier.fillMaxWidth().padding(
            top = 12.dp
        ).height(2.dp).background(
            color = Color(0xFFECECEC)
        ))
    }


}

@Preview
@Composable
private fun DownloadedFilesItemPrev() {
    //DownloadedFilesItem(File)
}
//fun getVideoThumbnail(file: File): Bitmap? {
//    return ThumbnailUtils.createVideoThumbnail(
//        file.absolutePath,
//        MediaStore.Video.Thumbnails.MINI_KIND
//    )
//}
//fun getFileSize(file: String): String {
//    if (!file.exists()) return "File not found"
//    val size = file.length() // Size in bytes
//    return formatFileSize(size)
//}
//private fun formatFileSize(size: Long): String {
//    if (size <= 0) return "0 B"
//    val units = arrayOf("B", "KB", "MB", "GB", "TB")
//    val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
//    return String.format("%.2f %s", size / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
//}
//fun getFileLastModified(file: File): String {
//    if (!file.exists()) return "File not found"
//    val dateModified = file.lastModified()
//    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//    return dateFormat.format(dateModified)
//}