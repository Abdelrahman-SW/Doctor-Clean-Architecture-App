package com.beapps.thedoctorapp.content.di

import android.app.Application
import androidx.room.Room
import com.beapps.thedoctorapp.content.data.local.db.NoteDb
import com.beapps.thedoctorapp.content.data.remote.firebase.FirebaseContentManager
import com.beapps.thedoctorapp.content.data.local.db.repo.NoteRepoImpl
import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.content.domain.NoteRepo
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
    fun provideContentManager(): ContentManager {
        return FirebaseContentManager()
    }

    @Provides
    @Singleton
    fun provideNoteDb(app: Application): NoteDb {
        return Room.databaseBuilder(
            app,
            NoteDb::class.java,
            NoteDb.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepo (noteDb: NoteDb) : NoteRepo {
        return NoteRepoImpl (noteDb)
    }
}