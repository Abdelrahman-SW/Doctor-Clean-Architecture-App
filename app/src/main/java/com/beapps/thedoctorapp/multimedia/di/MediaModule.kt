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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
object MediaModule {
    @ViewModelScoped
    @Provides
    fun provideMediaDownloader(): MediaDownloaderManager {
        return FirebaseMediaDownloader()
    }

    @ViewModelScoped
    @Provides
    fun provideMediaPlayer(app: Application): Player {
        return ExoPlayer.Builder(app).build()
    }
}