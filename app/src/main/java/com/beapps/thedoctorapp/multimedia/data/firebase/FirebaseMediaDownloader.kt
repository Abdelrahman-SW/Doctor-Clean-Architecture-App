package com.beapps.thedoctorapp.multimedia.data.firebase

import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import com.beapps.thedoctorapp.multimedia.domain.MediaDownloaderManager
import com.beapps.thedoctorapp.multimedia.domain.models.MediaContent
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class FirebaseMediaDownloader : MediaDownloaderManager {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    override suspend fun downloadMediaFile(
        path: String,
        mimeType: String?
    ): Result<MediaContent, Error.DownloadMediaErrors> {
        return when (mimeType.extractMimeType()) {
            FirebaseMimeType.Image -> downloadImage(path)
            FirebaseMimeType.Video -> downloadVideo(path)
            FirebaseMimeType.Text -> downloadTextFile(path)
//            FirebaseMimeType.Pdf -> downloadTextFile(path)
//            FirebaseMimeType.OctetStream -> downloadTextFile(path)
            FirebaseMimeType.Undefined -> Result.Error(Error.DownloadMediaErrors.UnSupportedMedia)
            FirebaseMimeType.Others -> Result.Error(Error.DownloadMediaErrors.UnSupportedMedia)
        }
    }

    private suspend fun downloadVideo(path: String): Result<MediaContent, Error.DownloadMediaErrors> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(
                    MediaContent.Video(
                        storageRef.child(path).downloadUrl.await().toString()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(Error.DownloadMediaErrors.Others(e.message))
            }

        }
    }

    private suspend fun downloadTextFile(filePath: String): Result<MediaContent, Error.DownloadMediaErrors> {
        return withContext(Dispatchers.IO) {
            val taskSnapshot = storageRef.child(filePath).stream.await()
            try {
                // Read the file content from the InputStream
                val inputStream: InputStream? = taskSnapshot?.stream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                // Read each line and append it to the StringBuilder
                var readerLine = reader.readLine()
                while (readerLine != null) {
                    stringBuilder.append(readerLine).append("\n")
                    readerLine = reader.readLine()
                }
                // Close the reader
                reader.close()
                Result.Success(data = MediaContent.TextFile(stringBuilder.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(Error.DownloadMediaErrors.Others(e.message))
            }
        }
    }

    private suspend fun downloadImage(filePath: String): Result<MediaContent, Error.DownloadMediaErrors> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(
                    MediaContent.Image(
                        storageRef.child(filePath).downloadUrl.await().toString()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(Error.DownloadMediaErrors.Others(e.message))
            }

        }
    }
}