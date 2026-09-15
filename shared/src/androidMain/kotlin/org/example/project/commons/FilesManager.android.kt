package org.example.project.commons

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import org.example.project.domain.models.appmodels.MediaFile
import org.example.project.domain.models.appmodels.MediaFolder
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidFileManager(private val context: Context) : FileManager {
    override suspend fun loadMedia(): Map<String, MediaFolder> {
        val foldersMap = mutableMapOf<String, MediaFolder>()
        val collection = MediaStore.Files.getContentUri("external")

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,     // ✅ filename
            MediaStore.Files.FileColumns.SIZE,             // ✅ size
            MediaStore.Files.FileColumns.DATE_MODIFIED,    // ✅ date modified
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.RELATIVE_PATH,     // safer than DATA
            MediaStore.Video.Media.DURATION
        )

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"

        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

        context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)
            val folderColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.RELATIVE_PATH)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn) ?: ""
                val size = cursor.getLong(sizeColumn)
                val dateModified = cursor.getLong(dateColumn) * 1000 // ⚠️ convert to millis
                val folderName = cursor.getString(folderColumn) ?: "Unknown"
                val path = cursor.getString(pathColumn) ?: ""

                val uri = ContentUris.withAppendedId(collection, id)
                val duration = cursor.getLong(durationColumn) // in milliseconds

                val mediaFile = MediaFile(
                    uri = uri.toString(),
                    path = path,
                    folderName = folderName,
                    name = name,
                    size = size,
                    dateModified = dateModified,
                    thumbnail = uri.toString(),
                    duration = duration
                )

                val folder = foldersMap.getOrPut(folderName) {
                    MediaFolder(folderName, mutableListOf())
                }

                folder.files.add(mediaFile)
            }
        }

        return foldersMap
    }

    override suspend fun loadVideos(): List<MediaFile> {
        val videoList = mutableListOf<MediaFile>()
        val collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.RELATIVE_PATH,
            MediaStore.Video.Media.DURATION
        )

        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RELATIVE_PATH)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn) ?: ""
                val size = cursor.getLong(sizeColumn)
                val dateModified = cursor.getLong(dateColumn) * 1000
                val path = cursor.getString(pathColumn) ?: ""
                val duration = cursor.getLong(durationColumn)

                val uri = ContentUris.withAppendedId(collection, id)

                val mediaFile = MediaFile(
                    uri = uri.toString(),
                    path = path,
                    folderName = "",
                    name = name,
                    size = size,
                    dateModified = dateModified,
                    thumbnail = uri.toString(),
                    duration = duration
                )

                videoList.add(mediaFile)
            }
        }

        return videoList
    }
}

actual fun provideFilesManagerModule(): Module {
    return module {
        single<FileManager> {
            AndroidFileManager(get())
        }
    }
}