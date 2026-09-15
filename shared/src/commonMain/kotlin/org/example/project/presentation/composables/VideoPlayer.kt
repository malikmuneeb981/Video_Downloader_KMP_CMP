package org.example.project.presentation.composables

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.*
import kotlinx.coroutines.delay
import org.example.project.commons.NativeVideoPlayerController
import org.example.project.commons.NativeVideoSurface
import org.example.project.commons.VideoResizeMode
import org.example.project.commons.rememberNativeVideoPlayer
import org.example.project.commons.rememberPlatformVideoUtils
import org.example.project.domain.models.appmodels.MediaFile
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.MediaReaderViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.max

@Composable
fun VideoPlayer(
    initialMediaSource: String,
    videoTitle: String = "Video Player",
    downloaderViewModel: DownloaderViewModel,
    mediaReaderViewModel: MediaReaderViewModel,
    onBackClick: () -> Unit = {}
) {
    val platformUtils = rememberPlatformVideoUtils()
    val downloadedFiles by downloaderViewModel.downloadedFilesState.collectAsStateWithLifecycle()
    val allVideoFiles by mediaReaderViewModel.videoList.collectAsStateWithLifecycle()

    val queueFiles: List<MediaFile> = remember(allVideoFiles, downloaderViewModel.selectedFolderWithFiles) {
        val folderFiles = downloaderViewModel.selectedFolderWithFiles?.files
        if (!folderFiles.isNullOrEmpty()) folderFiles else allVideoFiles
    }

    var currentSource by remember { mutableStateOf(initialMediaSource) }
    var currentTitle by remember { mutableStateOf(videoTitle) }

    var isLooping by remember { mutableStateOf(false) }

    val player = rememberNativeVideoPlayer(
        source = currentSource,
        autoPlay = true,
        isLooping = isLooping
    )

    var showControls by remember { mutableStateOf(true) }
    var resizeMode by remember { mutableStateOf(VideoResizeMode.Fit) }
    var isLocked by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }
    var playbackSpeed by remember { mutableStateOf(1f) }

    // Panels state
    var showQueuePanel by remember { mutableStateOf(false) }
    var showSettingsPanel by remember { mutableStateOf(false) }
    var isNightMode by remember { mutableStateOf(false) }

    // Seeking & Gestures
    var seekText by remember { mutableStateOf("") }
    var showSeekText by remember { mutableStateOf(false) }
    var brightness by remember { mutableStateOf(platformUtils.getScreenBrightness()) }
    var volume by remember { mutableStateOf(1f) }

    // Auto-hide controls
    LaunchedEffect(showControls, player.isPlaying, isLocked, showSettingsPanel, showQueuePanel) {
        if (showControls && player.isPlaying && !isLocked && !showSettingsPanel && !showQueuePanel) {
            delay(4000)
            showControls = false
        }
    }
    DisposableEffect(Unit){
        onDispose {
            platformUtils.toggleOrientation(true)
        }
    }

    LaunchedEffect(showSeekText) {
        if (showSeekText) {
            delay(800)
            showSeekText = false
        }
    }

    fun playNext() {
        val currentIndex = queueFiles.indexOfFirst { it.uri == currentSource || it.path == currentSource }
        if (currentIndex != -1 && currentIndex < queueFiles.size - 1) {
            val next = queueFiles[currentIndex + 1]
            currentSource = next.uri.ifBlank { next.path }
            currentTitle = next.name
        }
    }

    fun playPrevious() {
        val currentIndex = queueFiles.indexOfFirst { it.uri == currentSource || it.path == currentSource }
        if (currentIndex > 0) {
            val prev = queueFiles[currentIndex - 1]
            currentSource = prev.uri.ifBlank { prev.path }
            currentTitle = prev.name
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val isLandscape = maxWidth > maxHeight

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { offset ->
                            if (isLocked) return@detectTapGestures
                            if (offset.x < size.width * 0.33f) {
                                val newPos = max(0L, player.currentPositionMs - 10000)
                                player.seekTo(newPos)
                                seekText = "-10s"
                                showSeekText = true
                            } else if (offset.x > size.width * 0.66f) {
                                val newPos = (player.currentPositionMs + 10000).coerceAtMost(player.durationMs)
                                player.seekTo(newPos)
                                seekText = "+10s"
                                showSeekText = true
                            }
                        },
                        onTap = {
                            if (!isLocked) {
                                showControls = !showControls
                                if (showControls) {
                                    showQueuePanel = false
                                    showSettingsPanel = false
                                }
                            }
                        }
                    )
                }
        ) {
            // 🎬 Native Hardware Video Surface (ExoPlayer on Android, AVPlayer on iOS)
            NativeVideoSurface(
                controller = player,
                resizeMode = resizeMode,
                modifier = Modifier.fillMaxSize()
            )

            // Night mode overlay
            if (isNightMode) {
                Box(modifier = Modifier.fillMaxSize().background(Color(0x66000000)))
            }

            // Gesture Overlay for Brightness & Volume
            if (!isLocked && !showQueuePanel && !showSettingsPanel) {
                GestureOverlay(
                    brightness = brightness,
                    onBrightnessChange = { newBrightness ->
                        brightness = newBrightness
                        platformUtils.setScreenBrightness(newBrightness)
                    },
                    volume = volume,
                    onVolumeChange = { newVolume ->
                        volume = newVolume
                        player.setVolume(newVolume)
                        isMuted = newVolume == 0f
                    }
                )
            }

            // Double tap seek indicator
            AnimatedVisibility(
                visible = showSeekText,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = seekText,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }

            // Unlock button
            if (isLocked) {
                IconButton(
                    onClick = { isLocked = false },
                    modifier = Modifier
                        .padding(32.dp)
                        .align(Alignment.CenterStart)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_locked_vid_player),
                        contentDescription = "Unlock",
                        tint = Color.White
                    )
                }
            }

            // Main Controls Overlay
            AnimatedVisibility(
                visible = showControls && !isLocked,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    // Top Bar (padded for status bars and device notch/cutout)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .windowInsetsPadding(WindowInsets.displayCutout)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .align(Alignment.TopStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        Text(
                            text = currentTitle,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        IconButton(onClick = { platformUtils.toggleOrientation(isLandscape) }) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_screen_rotation_vid_player),
                                contentDescription = "Rotate",
                                tint = Color.White
                            )
                        }
                        if (queueFiles.isNotEmpty()) {
                            IconButton(onClick = {
                                showQueuePanel = !showQueuePanel
                                showSettingsPanel = false
                            }) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_queue_vid_player),
                                    contentDescription = "Queue",
                                    tint = Color.White
                                )
                            }
                        }
                        IconButton(onClick = {
                            showSettingsPanel = !showSettingsPanel
                            showQueuePanel = false
                        }) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_more_vid_player),
                                contentDescription = "More",
                                tint = Color.White
                            )
                        }
                    }

                    // Bottom Controls Bar (padded for navigation bars and device cutout)
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .windowInsetsPadding(WindowInsets.displayCutout)
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        // Custom Slider
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = formatTime(player.currentPositionMs), color = Color.White, fontSize = 12.sp)

                            Slider(
                                value = if (player.durationMs > 0) (player.currentPositionMs.toFloat() / player.durationMs).coerceIn(0f, 1f) else 0f,
                                onValueChange = { progress ->
                                    val targetMs = (progress * player.durationMs).toLong()
                                    player.seekTo(targetMs)
                                },
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.White,
                                    activeTrackColor = Color(0xFF1E4DFF),
                                    inactiveTrackColor = Color.White.copy(alpha = 0.4f)
                                ),
                                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                            )

                            Text(text = formatTime(player.durationMs), color = Color.White, fontSize = 12.sp)
                        }

                        // Action Icons Row
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left: Lock & Loop
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { isLocked = true }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_unlocked_vid_player),
                                        contentDescription = "Lock",
                                        tint = Color.White
                                    )
                                }
                                IconButton(onClick = {
                                    isLooping = !isLooping
                                    player.setLooping(isLooping)
                                }) {
                                    Icon(
                                        painter = painterResource(if (isLooping) Res.drawable.ic_repeatmode_one else Res.drawable.ic_repeatmode_repeat),
                                        contentDescription = "Loop",
                                        tint = if (isLooping) Color(0xFF1E4DFF) else Color.White
                                    )
                                }
                            }

                            // Center: Prev, Play/Pause, Next
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { playPrevious() }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_previous_vid_player),
                                        contentDescription = "Prev",
                                        tint = Color.White
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(52.dp)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                listOf(Color(0xFF2761FB), Color(0xFF0C3AED))
                                            ),
                                            shape = CircleShape
                                        )
                                        .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                                        .clickable {
                                            if (player.isPlaying) player.pause() else player.play()
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(if (player.isPlaying) Res.drawable.ic_pause_vid_player else Res.drawable.ic_play_vid_player),
                                        contentDescription = "Play/Pause",
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }

                                IconButton(onClick = { playNext() }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_next_vid_player),
                                        contentDescription = "Next",
                                        tint = Color.White
                                    )
                                }
                            }

                            // Right: Crop, Mute, Speed
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {
                                    resizeMode = when (resizeMode) {
                                        VideoResizeMode.Fit -> VideoResizeMode.Zoom
                                        VideoResizeMode.Zoom -> VideoResizeMode.Fill
                                        VideoResizeMode.Fill -> VideoResizeMode.Fit
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_crop_vid_player),
                                        contentDescription = "Resize",
                                        tint = Color.White
                                    )
                                }

                                IconButton(onClick = {
                                    isMuted = !isMuted
                                    player.setVolume(if (isMuted) 0f else 1f)
                                }) {
                                    Icon(
                                        if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Mute",
                                        tint = Color.White
                                    )
                                }

                                Text(
                                    text = "${playbackSpeed}x",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White.copy(alpha = 0.2f))
                                        .clickable {
                                            playbackSpeed = when (playbackSpeed) {
                                                1f -> 1.5f
                                                1.5f -> 2f
                                                2f -> 0.5f
                                                else -> 1f
                                            }
                                            player.setPlaybackSpeed(playbackSpeed)
                                        }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Settings Panel
            AnimatedVisibility(
                visible = showSettingsPanel && showControls,
                enter = if (isLandscape) slideInHorizontally(initialOffsetX = { it }) + fadeIn() else slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = if (isLandscape) slideOutHorizontally(targetOffsetX = { it }) + fadeOut() else slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = if (isLandscape) Modifier.align(Alignment.CenterEnd).fillMaxHeight().width(320.dp) else Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(250.dp)
            ) {
                SettingsPanel(
                    isLandscape = isLandscape,
                    brightness = brightness,
                    onBrightnessChange = {
                        brightness = it
                        platformUtils.setScreenBrightness(it)
                    },
                    volume = volume,
                    onVolumeChange = {
                        volume = it
                        player.setVolume(it)
                        isMuted = it == 0f
                    },
                    isNightMode = isNightMode,
                    onNightModeToggle = { isNightMode = !isNightMode },
                    onPipClick = { platformUtils.enterPictureInPicture() },
                    onClose = { showSettingsPanel = false }
                )
            }

            // Queue Panel
            AnimatedVisibility(
                visible = showQueuePanel && showControls,
                enter = if (isLandscape) slideInHorizontally(initialOffsetX = { it }) else slideInVertically(initialOffsetY = { it }),
                exit = if (isLandscape) slideOutHorizontally(targetOffsetX = { it }) else slideOutVertically(targetOffsetY = { it }),
                modifier = if (isLandscape) Modifier.align(Alignment.CenterEnd).fillMaxHeight().width(320.dp) else Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(360.dp)
            ) {
                QueuePanel(
                    queueFiles = queueFiles,
                    onVideoSelect = { selectedFile ->
                        currentSource = selectedFile.uri.ifBlank { selectedFile.path }
                        currentTitle = selectedFile.name
                        showQueuePanel = false
                    },
                    onClose = { showQueuePanel = false }
                )
            }
        }
    }
}

// Helpers
fun formatTime(timeMs: Long): String {
    val totalSeconds = timeMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}

@Composable
fun SettingsPanel(
    isLandscape: Boolean,
    brightness: Float,
    onBrightnessChange: (Float) -> Unit,
    volume: Float,
    onVolumeChange: (Float) -> Unit,
    isNightMode: Boolean,
    onNightModeToggle: () -> Unit,
    onPipClick: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .windowInsetsPadding(WindowInsets.displayCutout)
            .padding(16.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Settings", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(modifier = Modifier.clickable { onPipClick(); onClose() }, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_pip_vid_player),
                        contentDescription = "PIP",
                        tint = Color.White
                    )
                    Text("PIP", color = Color.White, fontSize = 11.sp)
                }
                Column(modifier = Modifier.clickable { onNightModeToggle() }, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_night_vid_player),
                        contentDescription = "Night",
                        tint = if (isNightMode) Color(0xFF1E4DFF) else Color.White
                    )
                    Text("Night", color = if (isNightMode) Color(0xFF1E4DFF) else Color.White, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BrightnessMedium, contentDescription = null, tint = Color.White)
                Slider(value = brightness, onValueChange = onBrightnessChange, modifier = Modifier.weight(1f).padding(horizontal = 8.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = Color.White)
                Slider(value = volume, onValueChange = onVolumeChange, modifier = Modifier.weight(1f).padding(horizontal = 8.dp))
            }
        }
    }
}

@Composable
fun QueuePanel(queueFiles: List<MediaFile>, onVideoSelect: (MediaFile) -> Unit, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .windowInsetsPadding(WindowInsets.displayCutout)
            .padding(16.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Queue", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(queueFiles) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { onVideoSelect(item) }.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = item.uri.ifBlank { item.path },
                            contentDescription = item.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(64.dp, 48.dp).clip(RoundedCornerShape(8.dp)).background(Color.DarkGray)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = item.name, color = Color.White, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(text = "${item.size / (1024 * 1024)} MB", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GestureOverlay(brightness: Float, onBrightnessChange: (Float) -> Unit, volume: Float, onVolumeChange: (Float) -> Unit) {
    var isChangingBrightness by remember { mutableStateOf(false) }
    var isChangingVolume by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        if (offset.x < size.width / 2) isChangingBrightness = true else isChangingVolume = true
                    },
                    onDragEnd = { isChangingBrightness = false; isChangingVolume = false },
                    onDragCancel = { isChangingBrightness = false; isChangingVolume = false },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val sensitivity = 0.003f
                        if (isChangingBrightness) {
                            onBrightnessChange((brightness - dragAmount.y * sensitivity).coerceIn(0f, 1f))
                        } else if (isChangingVolume) {
                            onVolumeChange((volume - dragAmount.y * sensitivity).coerceIn(0f, 1f))
                        }
                    }
                )
            }
    )
}
