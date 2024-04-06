package com.beapps.thedoctorapp.di

import com.beapps.thedoctorapp.auth.data.firebase.FirebaseAuthManager
import com.beapps.thedoctorapp.auth.domain.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthManager() : AuthManager {
        return FirebaseAuthManager()
    }
}