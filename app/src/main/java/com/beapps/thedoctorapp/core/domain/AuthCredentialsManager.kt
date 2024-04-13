package com.beapps.thedoctorapp.core.domain

import com.beapps.thedoctorapp.auth.domain.Doctor

interface AuthCredentialsManager {
    fun saveDoctorCredentials(doctor: Doctor)

    fun deleteDoctorCredentials()
    fun isDoctorLoggedIn(): Boolean

    fun getCurrentLoggedInDoctor(): Doctor?
}