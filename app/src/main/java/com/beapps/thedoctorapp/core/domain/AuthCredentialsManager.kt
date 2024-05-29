package com.beapps.thedoctorapp.core.domain

interface AuthCredentialsManager {
    fun saveDoctorCredentials(doctor: Doctor)

    fun deleteDoctorCredentials()
    fun isDoctorLoggedIn(): Boolean

    fun getCurrentLoggedInDoctor(): Doctor?
}