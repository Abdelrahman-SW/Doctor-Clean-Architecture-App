package com.beapps.thedoctorapp.multimedia.di

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.beapps.thedoctorapp.multimedia.data.firebase.FirebaseMediaDownloader
import com.beapps.thedoctorapp.multimedia.domain.MediaDownloaderManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideMediaPlayer(app : Application) : Player {
        return ExoPlayer.Builder(app).build()
    }
}