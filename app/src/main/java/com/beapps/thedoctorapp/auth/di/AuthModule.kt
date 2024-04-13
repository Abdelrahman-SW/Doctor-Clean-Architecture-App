package com.beapps.thedoctorapp.auth.di

import android.content.Context
import com.beapps.thedoctorapp.auth.data.remote.firebase.FirebaseAuthManager
import com.beapps.thedoctorapp.auth.domain.AuthManager
import com.beapps.thedoctorapp.core.data.sharedPrefs.SharedPrefsAuthCredentialsManager
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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