package com.beapps.thedoctorapp.multimedia.di

import com.beapps.thedoctorapp.multimedia.data.FirebaseMediaDownloader
import com.beapps.thedoctorapp.multimedia.domain.MediaDownloaderManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MediaModule {
    @Singleton
    @Provides
    fun provideMediaDownloader() : MediaDownloaderManager {
        return FirebaseMediaDownloader()
    }
}