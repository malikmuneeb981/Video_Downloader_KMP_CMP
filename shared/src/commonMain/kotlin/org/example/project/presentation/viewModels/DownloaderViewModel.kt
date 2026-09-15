package org.example.project.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion.DailyMotionSearchResponse
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion.DailyMotionVideosResponse
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.app_name
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.commons.AppUtilFunctions
import org.example.project.commons.DownloadFileManager
import org.example.project.domain.models.apiModels.VideoDownloaderModel
import org.example.project.domain.models.appmodels.MediaFolder
import org.example.project.utils.DataStorePreferences
import org.example.project.utils.PreferencesKeys
import kotlin.collections.emptyList

class DownloaderViewModel(private val dataStorePreferences: DataStorePreferences,
   private val downloadFileManager: DownloadFileManager,
    private val appUtilFunctions: AppUtilFunctions): ViewModel() {

    val Terms_of_Service = ""

    var normalWAOrBusiness = 1

    var urlToLoad: String  ?= null

    val Privacy_Policy = "http://google.com"
    private val _extractedTextFromIntent = MutableStateFlow("")
    val extractedTextFromIntent = _extractedTextFromIntent.asStateFlow()
    private val _languageSelectedState = MutableStateFlow<String>("")
    val languageSelectedState = _languageSelectedState.asStateFlow()
    private val _isDarkTheme = MutableStateFlow<Boolean?>(null)
    val isDarkTheme = _isDarkTheme.asStateFlow()
    private val _isFirstTime = MutableStateFlow(true)
    val isFirstTime = _isFirstTime.asStateFlow()
    private val _outSideAppDownload = MutableStateFlow(false)
    val outSideAppDownload = _outSideAppDownload.asStateFlow()
    var videoToPlayUrl: String ?= null
    var videoToPlayFile: String ?= null

    var videoToPlayUri: String ?= null

  //  var favReelSelectedIndex: LikedReels ?=null

    var selectedFolderWithFiles: MediaFolder?=null
    private val _downloadedFilesState = MutableStateFlow<List<String>>(emptyList())
    val downloadedFilesState: StateFlow<List<String>> get() = _downloadedFilesState

    private val _downloadProgress = MutableStateFlow(mapOf<Long, Int>())
    val downloadProgress: StateFlow<Map<Long, Int>> = _downloadProgress.asStateFlow()
    private val _downloadingThumbnail = MutableStateFlow(mapOf<Long, String>())

    val downloadingThumbnail: StateFlow<Map<Long, String>> = _downloadingThumbnail.asStateFlow()

    private var _statusSaverPerm = MutableStateFlow<String>("")
    val statusSaverPerm = _statusSaverPerm.asStateFlow<String>()

    private var _reelsMute = MutableStateFlow<Boolean>(false)
    val reelsMute = _reelsMute.asStateFlow<Boolean>()
    private var _downloadingProgress = MutableStateFlow<Int?>(null)
    val downloadingProgress = _downloadingProgress.asStateFlow<Int?>()

    var trendingItemSelected = DailyMotionVideosResponse.Category()
    var searchedVideosResponse: DailyMotionSearchResponse?= null

    var selectedSocialMedia = ""

    var videoToDownloadResponse: VideoDownloaderModel?=null

    val WAUri =
        "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
    val WABusinessUri =
        "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia%2F.Statuses"

    var statusSaverSelectedFile: String ?= null

    val appName = Res.string.app_name

    fun setExtractedTextFromIntent(text: String?)=viewModelScope.launch{
        text?.let {
            _extractedTextFromIntent.emit(text)
        }

    }
    fun checkIsFirstTime(){
        viewModelScope.launch {
            dataStorePreferences.getBoolean(PreferencesKeys.IS_FIRST_TIME).collect {
                it?.let {
                    _isFirstTime.emit(it)
                }

            }

        }
    }
    fun checkOutSideDownloadAllowed(){
        viewModelScope.launch {
            dataStorePreferences.getBoolean(PreferencesKeys.OUTSIDE_APP_DOWNLOADER).collect {
                it?.let {
                    _outSideAppDownload.emit(it)
                }

            }

        }
    }
    fun normalWsUriPathPrefs(): Boolean {
        checkStatusSaverPermission()
        val normalWsUriPath = _statusSaverPerm.value
        return normalWsUriPath == WAUri

    }

    fun saveInt(key:String, lang: Int) = viewModelScope.launch(Dispatchers.IO) {

        dataStorePreferences.saveInt(key,lang)

    }

    fun saveString(key:String,value: String) = viewModelScope.launch(Dispatchers.IO) {

        dataStorePreferences.saveString(key,value)

    }
    fun saveBoolean(key:String,value: Boolean) = viewModelScope.launch(Dispatchers.IO) {

        dataStorePreferences.saveBoolean(key,value)

    }
    fun checkStatusSaverPermission() = viewModelScope.launch(Dispatchers.IO) {
        dataStorePreferences.getString(PreferencesKeys.Status_Saver_Permission).collect { appFirstTime->
            appFirstTime?.let {
                _statusSaverPerm.emit(it)
            }

        }
    }
    fun checkReelsMute() = viewModelScope.launch(Dispatchers.IO) {
        dataStorePreferences.getBoolean(PreferencesKeys.REELS_MUTE).collect { muted->
            muted?.let {
                _reelsMute.emit(it)
            }

        }
    }
    fun setReelsMute(muted: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStorePreferences.saveBoolean(PreferencesKeys.REELS_MUTE, muted)
        _reelsMute.emit(muted)
    }
    fun updateProgress(id: Long, progress: Int)=viewModelScope.launch(Dispatchers.IO) {
        _downloadProgress.value = _downloadProgress.value.toMutableMap().apply {
            this[id] = progress
        }
    }

    fun removeDownload(id: Long)=viewModelScope.launch(Dispatchers.IO) {
        _downloadProgress.value = _downloadProgress.value.toMutableMap().apply {
            remove(id)
        }
    }

    fun removeDownloadFromDownloads(id: Long)=viewModelScope.launch(Dispatchers.IO) {
        downloadFileManager.removeDownload(downloadId = id)
    }
    fun addDownloadingVidThumbnail(id: Long, thumbnail: String)=viewModelScope.launch(Dispatchers.IO) {
        _downloadingThumbnail.value = _downloadingThumbnail.value.toMutableMap().apply {
            this[id] = thumbnail
        }
    }

    fun removeDownloadingVidThumbnail(id: Long)=viewModelScope.launch(Dispatchers.IO) {
        _downloadingThumbnail.value = _downloadingThumbnail.value.toMutableMap().apply {
            remove(id)
        }
    }

    fun getCurrentLanguage()=viewModelScope.launch(Dispatchers.IO) {
        dataStorePreferences.getString(PreferencesKeys.LANGUAGE_SELECTED).collect { lang->
            lang?.let {
                _languageSelectedState.emit(it)
            }

        }
    }
    fun downloadFile(videolink: String):Long {
        return downloadFileManager.downloadFile(videolink)
    }
    fun getAllVideosInFolder(statuses: Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
        _downloadedFilesState.emit(downloadFileManager.getDownloadedFiles(statuses = statuses, appName = Res.string.app_name))
    }
    fun getDownloadProgress(downloadId: Long) = viewModelScope.launch(Dispatchers.IO) {
        _downloadingProgress.emit(downloadFileManager.getDownloadProgress(downloadId = downloadId))
    }
//    fun downloadStatus(
//        isVideo: Boolean,
//        context: Context,
//        fileUri: String,
//        fileName: String
//    ) {
//
//        val resolver = context.contentResolver
//
//        val collection = if (isVideo)
//            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//        else
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//
//        val contentValues = ContentValues().apply {
//            put(
//                MediaStore.MediaColumns.DISPLAY_NAME,
//                fileName
//            )
//
//            put(
//                MediaStore.MediaColumns.MIME_TYPE,
//                if (isVideo) "video/mp4" else "image/jpeg"
//            )
//
//            put(
//                MediaStore.MediaColumns.RELATIVE_PATH,
//
//                Environment.DIRECTORY_DCIM + "/$appName/Downloaded Statuses"
//
//            )
//        }
//
//        val uri = resolver.insert(collection, contentValues)
//
//        if (uri != null) {
//            resolver.openOutputStream(uri)?.use { output ->
//                val input = resolver.openInputStream(fileUri.toUri())
//
//                input?.use {
//                    it.copyTo(output)
//                }
//            }
//        }
//    }
//    fun shareFile(context: Context, path: String, isVideo: Boolean) {
//
//        val uri = if (path.startsWith("content://")) {
//            Uri.parse(path) // ✅ already content URI
//        } else {
//            val file = File(path)
//            FileProvider.getUriForFile(
//                context,
//                "${context.packageName}.provider",
//                file
//            )
//        }
//
//        val intent = Intent(Intent.ACTION_SEND).apply {
//            type = if (isVideo) "video/*" else "image/*"
//            putExtra(Intent.EXTRA_STREAM, uri)
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        }
//
//        context.startActivity(Intent.createChooser(intent, "Share via"))
//    }
    fun deleteFile(path: String)=viewModelScope.launch(Dispatchers.IO) {
        downloadFileManager.deleteFileFromFolder(filePath = path)
    }
//    fun isWhatsAppInstalled(context: Context): Boolean {
//        return try {
//            context.packageManager.getApplicationInfo("com.whatsapp", 0)
//            true
//        } catch (e: PackageManager.NameNotFoundException) {
//            false
//        }
//    }
//    fun isWhatsAppBusinessInstalled(context: Context): Boolean {
//        return try {
//            context.packageManager.getApplicationInfo("com.whatsapp.w4b", 0)
//            true
//        } catch (e: PackageManager.NameNotFoundException) {
//            false
//        }
//    }
//    fun uriToFile(context: Context, uriString: String): File? {
//        return try {
//            val uri = uriString.toUri()
//            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
//
//            val fileName = "temp_${System.currentTimeMillis()}.mp4"
//            val file = File(context.cacheDir, fileName)
//
//            val outputStream = FileOutputStream(file)
//
//            inputStream.use { input ->
//                outputStream.use { output ->
//                    input.copyTo(output)
//                }
//            }
//
//            file
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
    fun shareText(text: String, title: String = "Share via") {
      appUtilFunctions.shareText(text = text)
    }
//    fun setDarkTheme(enabled: Boolean?)=viewModelScope.launch {
//        when(enabled){
//            true->{
//                dataStorePreferences.saveInt(PreferencesKeys.AppTheme,1)
//            }
//            false->{
//                dataStorePreferences.saveInt(PreferencesKeys.AppTheme,2)
//            }
//            else->{
//                dataStorePreferences.saveInt(PreferencesKeys.AppTheme,0)
//            }
//        }
//    }
//    fun getAppTheme()=viewModelScope.launch(Dispatchers.IO) {
//        dataStorePreferences.getInt(PreferencesKeys.AppTheme).collect { Theme->
//            when(Theme){
//                0->{
//                    _isDarkTheme.emit(null)
//                }
//                1->{
//                    _isDarkTheme.emit(true)
//                }
//                2->{
//                    _isDarkTheme.emit(false)
//                }
//            }
//        }
//
//    }
//    fun shareTxt(mContext: Context, shareableTxt: String,showSnack:(String)-> Unit) {
//        if (shareableTxt.isBlank()){
//            showSnack("No text to share...")
//        }else{
//            val intent = Intent()
//            intent.action = Intent.ACTION_SEND
//            intent.type = "text/plain"
//            intent.putExtra(Intent.EXTRA_TEXT, shareableTxt)
//            mContext.startActivity(Intent.createChooser(intent, "Share with:"))
//        }
//
//    }
    fun openLink(url:String) {
      appUtilFunctions.openLink(url = url)
    }
//    fun openInAppSubscriptions(activity: Activity) {
//        try {
//            AdsUtils.blockAppOpenAd = true
//            val browserIntent = Intent(
//                Intent.ACTION_VIEW,
//                "https://play.google.com/store/account/subscriptions".toUri()
//            )
//            activity.startActivity(browserIntent)
//        } catch (e: Exception) {
//
//        }
//
//
//    }
//    fun openAppInPlayStore(activity: Activity) {
//        val appPackageName = activity.packageName
//        try {
//            val intent = Intent(Intent.ACTION_VIEW, "market://details?id=$appPackageName".toUri())
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            activity.startActivity(intent)
//        } catch (e: Exception) {
//
//        }
//    }
    fun setAppLocale(languageCode: String) {
        appUtilFunctions.changeAppLanguage(languageCode = languageCode)
    }
fun formatDurationHMS(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    // padStart(2, '0') ensures the number is always at least 2 digits long
    val minStr = minutes.toString().padStart(2, '0')
    val secStr = secs.toString().padStart(2, '0')
    return if (hours > 0) {
        val hourStr = hours.toString().padStart(2, '0')
        "$hourStr:$minStr:$secStr"
    } else {
        "$minStr:$secStr"
    }
}

}