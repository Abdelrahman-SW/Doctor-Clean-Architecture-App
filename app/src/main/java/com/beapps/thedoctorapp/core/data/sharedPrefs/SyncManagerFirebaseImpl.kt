package com.beapps.thedoctorapp.core.data.sharedPrefs

import com.beapps.thedoctorapp.core.domain.Doctor
import com.beapps.thedoctorapp.core.domain.SyncManager
import kotlinx.coroutines.delay

class SyncManagerFirebaseImpl : SyncManager {
    override suspend fun syncGraphsForDoctorPatients(doctor: Doctor) {

    }
}