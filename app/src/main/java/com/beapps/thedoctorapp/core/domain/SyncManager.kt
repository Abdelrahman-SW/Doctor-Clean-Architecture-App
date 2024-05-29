package com.beapps.thedoctorapp.core.domain

interface SyncManager {
    suspend fun syncGraphsForDoctorPatients (doctor: Doctor)

}