package com.beapps.thedoctorapp.multimedia.domain

import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result

interface MediaDownloaderManager {
    suspend fun downloadMediaFile(path : String , mimeTypes: MimeTypes) : Result<MediaType , Error.DownloadMediaErrors>
}