package com.beapps.thedoctorapp.content.presentation.patients

import androidx.lifecycle.ViewModel
import com.beapps.thedoctorapp.content.domain.ContentManager
import javax.inject.Inject

class PatientsViewModel @Inject constructor(
    private val contentManager: ContentManager
) : ViewModel() {
    fun displayAllPatient(doctorId : String) {
        contentManager.displayDoctorPatients(doctorId)
    }
}