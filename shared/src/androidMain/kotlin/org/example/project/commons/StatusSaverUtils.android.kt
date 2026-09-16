package org.example.project.commons

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.provider.MediaStore
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.WsStatusModel
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.grant_access
import downloaderkmpproductionapp.shared.generated.resources.ic_nodownloads
import downloaderkmpproductionapp.shared.generated.resources.we_need_permission
import kotlinx.coroutines.launch
import org.example.project.presentation.composables.AppMainButton
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.utils.PreferencesKeys
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File
import java.util.Arrays
import kotlin.text.insert
import kotlin.text.iterator
import kotlin.toString
import kotlin.use


class DownloadStatusAndroid(
    private val context: Context
):DownloadStatus{
    override suspend fun downloadStatus(
        isVideo: Boolean,
        fileUri: String,
        fileName: String,
        appName: String
    ) {
        val resolver = context.contentResolver

        val collection = if (isVideo)
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        else
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                fileName
            )

            put(
                MediaStore.MediaColumns.MIME_TYPE,
                if (isVideo) "video/mp4" else "image/jpeg"
            )

            put(
                MediaStore.MediaColumns.RELATIVE_PATH,

                Environment.DIRECTORY_DCIM + "/$appName/Downloaded Statuses"

            )
        }

        val uri = resolver.insert(collection, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri)?.use { output ->
                val input = resolver.openInputStream(fileUri.toUri())

                input?.use {
                    it.copyTo(output)
                }
            }
        }
    }

}




@Composable
actual fun RecentStatuses(
    photoOrVideo: Int,
    downloaderViewModel: DownloaderViewModel,
    onResult: (List<WsStatusModel>) -> Unit
)
{

    val activity = LocalActivity.current as Activity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    )
    { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val treeUri = result.data?.data

            treeUri?.let { uri ->

                // Save URI (like Hawk)
                downloaderViewModel.saveString(PreferencesKeys.Status_Saver_Permission,treeUri.toString())

                activity.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                val fileDoc = DocumentFile.fromTreeUri(activity, uri)

                val list = mutableListOf<WsStatusModel>()

                fileDoc?.listFiles()?.forEach { file ->

                    if (!file.name.orEmpty().endsWith(".nomedia")) {

                        val model = WsStatusModel(
                            file.name.orEmpty(),
                            file.uri.toString()
                        )

                        if (file.uri.toString().endsWith(".mp4")||file.uri.toString().endsWith(".jpg")) {
                            list.add(model)
                        }
                    }
                }

                onResult(list)
            }
        }
    }
    downloaderViewModel.checkStatusSaverPermission()
    val treeUri by downloaderViewModel.statusSaverPerm.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            val WAORWABUri = when(downloaderViewModel.normalWAOrBusiness){
                1->{
                    downloaderViewModel.WAUri
                }
                2->{
                    downloaderViewModel.WABusinessUri
                }
                else -> {
                    downloaderViewModel.WAUri
                }
            }
            if (treeUri!=WAORWABUri){
                Image(imageVector = vectorResource(
                    Res.drawable.ic_nodownloads
                ), contentDescription = null, modifier = Modifier.size(
                    120.dp
                ))
                AppText(
                    text = stringResource(Res.string.we_need_permission),
                )
                AppMainButton(text = stringResource(Res.string.grant_access),
                    modifier = Modifier.fillMaxWidth().padding(
                        horizontal = 50.dp
                    )
                        .clickable {
                            val storageManager =
                                activity.getSystemService(Context.STORAGE_SERVICE) as StorageManager

                            val intent =
                                storageManager.primaryStorageVolume.createOpenDocumentTreeIntent()

                            val targetDirectory = when(downloaderViewModel.normalWAOrBusiness){
                                1->{
                                    "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
                                }
                                2->{
                                    "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia%2F.Statuses"
                                }
                                else -> {
                                    "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
                                }
                            }


                            var uri =
                                intent.getParcelableExtra<Uri>("android.provider.extra.INITIAL_URI")!!

                            var scheme = uri.toString()
                            scheme = scheme.replace("/root/", "/document/")
                            scheme += "%3A$targetDirectory"

                            uri = scheme.toUri()

                            intent.putExtra("android.provider.extra.INITIAL_URI", uri)
                            intent.putExtra("android.content.extra.SHOW_ADVANCED", true)

                            launcher.launch(intent)
                        }
                        .padding(
                            vertical = 10.dp,
                            horizontal = 25.dp
                        ))

            }
            else{


                treeUri.let { uri ->

                    activity.contentResolver.takePersistableUriPermission(
                        uri.toUri(),
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    val fileDoc = DocumentFile.fromTreeUri(activity, uri.toUri())

                    val list = mutableListOf<WsStatusModel>()

                    fileDoc?.listFiles()?.forEach { file ->

                        if (!file.name.orEmpty().endsWith(".nomedia")) {

                            val model = WsStatusModel(
                                file.name.orEmpty(),
                                file.uri.toString()
                            )
                            if (photoOrVideo==1){
                                if (file.uri.toString().endsWith(".jpg")) {
                                    list.add(model)
                                }
                            }else{
                                if (file.uri.toString().endsWith(".mp4")) {
                                    list.add(model)
                                }
                            }
                        }
                    }

                    onResult(list)
                }
            }
        }
        else{
            val filesPath =
                File(Environment.getExternalStorageDirectory().absolutePath + "/WhatsApp/Media/.Statuses")


            val statusFiles = filesPath.listFiles()
            val list = mutableListOf<WsStatusModel>()
            if (statusFiles != null && statusFiles.size > 0) {
                Arrays.sort(statusFiles)
                for (statusFile in statusFiles) {
                    val wsStatusModelClass = WsStatusModel(
                        statusFile.name, statusFile.toUri().toString()
                    )

                    if (!statusFile.name.endsWith(".nomedia")) {
                        if (photoOrVideo==1){
                            if (statusFile.toURI().toString().endsWith(".jpg")) {
                                list.add(wsStatusModelClass)
                            }
                        }else{
                            if (statusFile.toURI().toString().endsWith(".mp4")) {
                                list.add(wsStatusModelClass)
                            }
                        }
                    }
                }
            }

            onResult(list)
        }

    }

}

actual fun provideDownloadStatusModule(): Module {
    return module {
        single<DownloadStatus>{
            DownloadStatusAndroid(get())
        }
    }
}