package com.beapps.thedoctorapp.multimedia.data

import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import com.beapps.thedoctorapp.multimedia.domain.MediaDownloaderManager
import com.beapps.thedoctorapp.multimedia.domain.MediaType
import com.beapps.thedoctorapp.multimedia.domain.MimeTypes
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
        mimeTypes: MimeTypes
    ): Result<MediaType, Error.DownloadMediaErrors> {
        return when (mimeTypes) {
            MimeTypes.Image -> downloadImage(path)
            MimeTypes.Video -> downloadTextFile(path)
            MimeTypes.Text -> downloadTextFile(path)
            MimeTypes.Pdf -> downloadTextFile(path)
            MimeTypes.OctetStream -> downloadTextFile(path)
            MimeTypes.Undefined -> downloadTextFile(path)
            MimeTypes.Others -> downloadTextFile(path)
        }
    }

    private suspend fun downloadTextFile(filePath: String): Result<MediaType, Error.DownloadMediaErrors> {
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
                Result.Success(data = MediaType.TextFile(stringBuilder.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(Error.DownloadMediaErrors.Others(e.message))
            }
        }
    }

    private suspend fun downloadImage(filePath: String): Result<MediaType, Error.DownloadMediaErrors> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(
                    MediaType.Image(
                        storageRef.child(filePath).getBytes(Long.MAX_VALUE).await()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(Error.DownloadMediaErrors.Others(e.message))
            }

        }
    }
}