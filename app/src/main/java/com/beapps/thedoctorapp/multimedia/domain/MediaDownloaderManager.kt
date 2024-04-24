package com.beapps.thedoctorapp.multimedia.domain

import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import com.beapps.thedoctorapp.multimedia.domain.models.MediaContent

interface MediaDownloaderManager {
    suspend fun downloadMediaFile(path : String, mimeType: String?) : Result<MediaContent, Error.DownloadMediaErrors>
}