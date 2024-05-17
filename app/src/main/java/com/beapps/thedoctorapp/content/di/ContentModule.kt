package com.beapps.thedoctorapp.content.di

import android.content.Context
import com.beapps.thedoctorapp.content.data.remote.firebase.ContentManagerFirebaseImpl
import com.beapps.thedoctorapp.content.data.remote.firebase.PatientNotesManagerFirebaseImpl
import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.content.domain.PatientNotesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ContentModule {
    @Provides
    @Singleton
    fun provideContentManager(): ContentManager {
        return ContentManagerFirebaseImpl()
    }


    @Provides
    @Singleton
    fun provideNoteRepo(@ApplicationContext context: Context): PatientNotesManager {
        return PatientNotesManagerFirebaseImpl(context)
    }
}