package org.example.project.presentation.btmSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.PermissionBtmSheetModel
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.cancel
import downloaderkmpproductionapp.shared.generated.resources.grant
import downloaderkmpproductionapp.shared.generated.resources.ic_videos_permission
import org.example.project.presentation.composables.AppMainButton
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionBtmSheet(permissionBtmSheetModel: PermissionBtmSheetModel,onDismiss:()-> Unit,
                       onGrantClick:()-> Unit,
                       onCancelClick:()-> Unit) {

    val btmSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = btmSheetState,
        onDismissRequest = {
            onDismiss()
        }, content = {

            Column(modifier = Modifier.fillMaxWidth().padding(
                top = 10.dp
            ).padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(imageVector = vectorResource(
                    Res.drawable.ic_videos_permission
                ), contentDescription = null)
                AppText(text = permissionBtmSheetModel.permissionTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(
                        top = 25.dp
                    ))
                AppText(text = permissionBtmSheetModel.permissionText,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(
                        top = 8.dp
                    ))


                AppMainButton(text = stringResource(Res.string.grant),
                    modifier = Modifier.fillMaxWidth().padding(
                        top = 35.dp,
                    ).clickable(indication = null,
                        interactionSource = remember
                        { MutableInteractionSource() }){
                        onGrantClick()
                    })
                Row(modifier = Modifier.fillMaxWidth().padding(
                    bottom = 15.dp, top = 15.dp
                ).clip(shape = RoundedCornerShape(
                    12.dp
                )).clickable{
                    onCancelClick()
                }.padding(
                    vertical = 10.dp
                ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {

                    AppText(
                        text = stringResource(Res.string.cancel),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(
                            start = 8.dp
                        )
                    )

                }
            }

        }, containerColor = Color.White
    )

}

@Preview
@Composable
private fun PermissionBtmSheetPrev() {
        PermissionBtmSheet(permissionBtmSheetModel =
            PermissionBtmSheetModel(
                image = Res.drawable.ic_videos_permission,
                permissionTitle = "Permission Needed",
                permissionText = "Permission Needed To Access Videos On Device"
            ),onDismiss = {

        }, onGrantClick = {

        }, onCancelClick = {

        })


}