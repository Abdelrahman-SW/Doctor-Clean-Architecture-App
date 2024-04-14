package com.beapps.thedoctorapp.content.di

import com.beapps.thedoctorapp.content.data.firebase.FirebaseContentManager
import com.beapps.thedoctorapp.content.domain.ContentManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ContentModule {
    @Provides
    @Singleton
    fun provideContentManager() : ContentManager {
        return FirebaseContentManager()
    }
}